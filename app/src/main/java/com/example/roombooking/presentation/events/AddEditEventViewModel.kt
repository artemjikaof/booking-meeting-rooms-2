package com.example.roombooking.presentation.events

import androidx.lifecycle.*
import com.example.roombooking.data.repository.EventRepository
import com.example.roombooking.data.repository.RoomRepository
import com.example.roombooking.domain.model.Event
import com.example.roombooking.domain.model.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
    data class ConflictDetected(val roomName: String, val time: String) : UiState()
}

@HiltViewModel
class AddEditEventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    val rooms: StateFlow<List<Room>> = roomRepository.getAllRooms()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _currentEvent = MutableStateFlow<Event?>(null)
    val currentEvent: StateFlow<Event?> = _currentEvent.asStateFlow()

    fun loadEvent(eventId: Long) {
        if (eventId <= 0L) return
        viewModelScope.launch {
            _currentEvent.value = eventRepository.getEventById(eventId)
        }
    }

    fun saveEvent(
        title: String,
        dateStart: String,
        dateEnd: String,
        timeStart: String,
        timeEnd: String,
        room: Room,
        description: String,
        participants: String,
        syncToCalendar: Boolean
    ) {
        if (title.isBlank()) {
            _uiState.value = UiState.Error("Введите название мероприятия")
            return
        }
        if (timeEnd <= timeStart && dateEnd == dateStart) {
            _uiState.value = UiState.Error("Время окончания должно быть позже начала")
            return
        }
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val excludeId = _currentEvent.value?.id ?: 0L
            val hasConflict = eventRepository.hasConflict(room.id, dateStart, timeStart, timeEnd, excludeId)
            if (hasConflict) {
                _uiState.value = UiState.ConflictDetected(room.name, "$timeStart–$timeEnd")
                return@launch
            }
            try {
                val event = Event(
                    id = _currentEvent.value?.id ?: 0L,
                    title = title, dateStart = dateStart, dateEnd = dateEnd,
                    timeStart = timeStart, timeEnd = timeEnd,
                    roomId = room.id, roomName = room.name,
                    description = description, participants = participants,
                    syncToDeviceCalendar = syncToCalendar,
                    deviceCalendarEventId = _currentEvent.value?.deviceCalendarEventId,
                    lastModifiedInApp = System.currentTimeMillis()
                )
                if (_currentEvent.value == null) eventRepository.insertEvent(event)
                else eventRepository.updateEvent(event)
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Ошибка сохранения")
            }
        }
    }

    fun deleteEvent() {
        val event = _currentEvent.value ?: return
        viewModelScope.launch {
            eventRepository.deleteEvent(event)
            _uiState.value = UiState.Success
        }
    }
}
