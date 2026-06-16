package com.example.roombooking.presentation.events

import android.util.Log
import androidx.lifecycle.*
import com.example.roombooking.data.repository.EventRepository
import com.example.roombooking.data.repository.RoomRepository
import com.example.roombooking.data.repository.YandexCalendarRepository
import com.example.roombooking.domain.model.Event
import com.example.roombooking.domain.model.Room
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
    data class ConflictDetected(val roomName: String, val time: String) : UiState()
    data class YandexConflictDetected(val summary: String, val time: String) : UiState()
}

@HiltViewModel
class AddEditEventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val roomRepository: RoomRepository,
    private val yandexRepository: YandexCalendarRepository
) : ViewModel() {

    val rooms: StateFlow<List<Room>> = roomRepository.getAllRooms()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var forceSave: Boolean = false
    fun setForceSave(value: Boolean) { forceSave = value }

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

            // Проверка конфликтов в Яндекс Календаре
            // ИСПРАВЛЕНО: getYandexEvents() без параметров, фильтруем по дате сами
            if (yandexRepository.isAuthorized() && !forceSave) {
                val yandexEvents = yandexRepository.getYandexEvents().getOrNull()
                val zone = ZoneId.systemDefault()

                yandexEvents?.forEach { yEvent ->
                    // ИСПРАВЛЕНО: dtStart/dtEnd — Long миллисекунды, конвертируем в время
                    val yStartLocal = Instant.ofEpochMilli(yEvent.dtStart)
                        .atZone(zone).toLocalDateTime()
                    val yEndLocal = Instant.ofEpochMilli(yEvent.dtEnd)
                        .atZone(zone).toLocalDateTime()

                    // Фильтруем только события на нужную дату
                    if (yStartLocal.toLocalDate().toString() != dateStart) return@forEach

                    val yStart = yStartLocal.toLocalTime().toString().substring(0, 5)
                    val yEnd = yEndLocal.toLocalTime().toString().substring(0, 5)

                    val isTimeConflict = timeStart < yEnd && timeEnd > yStart
                    // ИСПРАВЛЕНО: data.location (не dto.location)
                    val isLocationConflict = yEvent.location.contains(room.name, ignoreCase = true)

                    if (isTimeConflict && isLocationConflict) {
                        // ИСПРАВЛЕНО: data.title (не dto.summary)
                        _uiState.value = UiState.YandexConflictDetected(
                            summary = yEvent.title,
                            time = "$yStart–$yEnd"
                        )
                        return@launch
                    }
                }
            }

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

            try {
                if (_currentEvent.value == null) {
                    Log.d("SaveEvent", "INSERT event: $event")
                    eventRepository.insertEvent(event)
                } else {
                    Log.d("SaveEvent", "UPDATE event id=${event.id}: $event")
                    eventRepository.updateEvent(event)
                }
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                Log.e("SaveEvent", "Save failed", e)
                val msg = e.cause?.message ?: e.message ?: e.javaClass.simpleName
                _uiState.value = UiState.Error("Ошибка: $msg")
            }
        }
    }

    fun deleteEvent() {
        val event = _currentEvent.value ?: return
        viewModelScope.launch {
            try {
                eventRepository.deleteEvent(event)
            } catch (e: Exception) {
                Log.e("SaveEvent", "Delete failed", e)
            }
            _uiState.value = UiState.Success
        }
    }
}