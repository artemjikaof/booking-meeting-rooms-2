package com.example.roombooking.presentation.rooms

import androidx.lifecycle.*
import com.example.roombooking.data.repository.RoomRepository
import com.example.roombooking.domain.model.Room
import com.example.roombooking.presentation.events.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val rooms: StateFlow<List<Room>> = _searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            if (query.isBlank()) roomRepository.getAllRooms()
            else roomRepository.searchRooms(query)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // Текущее редактируемое помещение
    private val _editingRoom = MutableStateFlow<Room?>(null)
    val editingRoom: StateFlow<Room?> = _editingRoom.asStateFlow()

    fun setSearchQuery(query: String) { _searchQuery.value = query }

    fun loadRoom(roomId: Long) {
        if (roomId <= 0L) { _editingRoom.value = null; return }
        viewModelScope.launch {
            _editingRoom.value = roomRepository.getRoomById(roomId)
        }
    }

    fun saveRoom(name: String, capacity: Int, description: String) {
        if (name.isBlank()) { _uiState.value = UiState.Error("Введите название"); return }
        if (capacity <= 0) { _uiState.value = UiState.Error("Введите вместимость"); return }
        viewModelScope.launch {
            val editing = _editingRoom.value
            if (editing == null) {
                // Создание
                roomRepository.insertRoom(Room(name = name, capacity = capacity, description = description))
            } else {
                // Обновление
                roomRepository.updateRoom(editing.copy(name = name, capacity = capacity, description = description))
            }
            _editingRoom.value = null
            _uiState.value = UiState.Success
        }
    }

    fun deleteRoom(room: Room) {
        viewModelScope.launch {
            try {
                if (roomRepository.hasLinkedEvents(room.id)) {
                    _uiState.value = UiState.Error("К помещению привязаны мероприятия. Сначала удалите их.")
                    return@launch
                }
                roomRepository.deleteRoom(room)
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Ошибка удаления")
            }
        }
    }

    fun resetState() { _uiState.value = UiState.Idle }
}
