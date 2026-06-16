package com.example.roombooking.presentation.calendar

import androidx.lifecycle.*
import com.example.roombooking.data.repository.CalendarEventData
import com.example.roombooking.data.repository.EventRepository
import com.example.roombooking.data.repository.YandexCalendarRepository
import com.example.roombooking.domain.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val yandexRepository: YandexCalendarRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _yandexEvents = MutableStateFlow<List<Event>>(emptyList())

    val eventsForSelectedDate: StateFlow<List<Event>> = combine(
        _selectedDate.flatMapLatest { date -> eventRepository.getEventsByDate(date.toString()) },
        _yandexEvents,
        _selectedDate
    ) { localEvents, yandexEvents, selectedDate ->
        val filteredYandex = yandexEvents.filter { it.dateStart == selectedDate.toString() }
        (localEvents + filteredYandex).sortedBy { it.timeStart }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()

    private val _datesWithEvents = MutableStateFlow<Set<String>>(emptySet())
    val datesWithEvents: StateFlow<Set<String>> = _datesWithEvents.asStateFlow()

    private val _viewMode = MutableStateFlow(CalendarViewMode.MONTH)
    val viewMode: StateFlow<CalendarViewMode> = _viewMode.asStateFlow()

    init {
        loadDatesWithEvents()
        loadYandexEvents()
    }

    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        val monthOfDate = YearMonth.from(date)
        if (_currentMonth.value != monthOfDate) {
            _currentMonth.value = monthOfDate
            loadDatesWithEvents()
            loadYandexEvents()
        }
    }

    // ИСПРАВЛЕНО: getYandexEvents() без параметров, маппим CalendarEventData → Event
    private fun loadYandexEvents() {
        if (!yandexRepository.isAuthorized()) return
        viewModelScope.launch {
            val result = yandexRepository.getYandexEvents()
            result.onSuccess { calendarEvents ->
                val zone = ZoneId.systemDefault()
                _yandexEvents.value = calendarEvents.mapNotNull { event ->
                    calendarEventDataToEvent(event, zone)
                }
            }.onFailure { e ->
                android.util.Log.e("CalendarVM", "Failed to load Yandex events", e)
            }
        }
    }

    // Конвертация CalendarEventData (dtStart/dtEnd в мс) → доменный Event
    private fun calendarEventDataToEvent(data: CalendarEventData, zone: ZoneId): Event? {
        return try {
            val startLocal = Instant.ofEpochMilli(data.dtStart).atZone(zone).toLocalDateTime()
            val endLocal = Instant.ofEpochMilli(data.dtEnd).atZone(zone).toLocalDateTime()
            Event(
                // ИСПРАВЛЕНО: data.title (не dto.summary)
                title = "[Яндекс] ${data.title}",
                // ИСПРАВЛЕНО: из миллисекунд, а не dto.start.dateTime
                dateStart = startLocal.toLocalDate().toString(),
                dateEnd = endLocal.toLocalDate().toString(),
                timeStart = startLocal.toLocalTime().toString().substring(0, 5),
                timeEnd = endLocal.toLocalTime().toString().substring(0, 5),
                roomId = -1,
                roomName = data.location.ifBlank { "Яндекс Календарь" },
                description = data.description,
                fromDeviceCalendar = true
            )
        } catch (e: Exception) {
            android.util.Log.w("CalendarVM", "Failed to map Yandex event: ${data.title}", e)
            null
        }
    }

    fun selectToday() {
        _selectedDate.value = LocalDate.now()
        _currentMonth.value = YearMonth.now()
        loadDatesWithEvents()
        loadYandexEvents()
    }

    fun navigateToPreviousMonth() {
        when (_viewMode.value) {
            CalendarViewMode.MONTH -> {
                _currentMonth.value = _currentMonth.value.minusMonths(1)
                loadDatesWithEvents()
                loadYandexEvents()
            }
            CalendarViewMode.WEEK -> {
                _selectedDate.value = _selectedDate.value.minusWeeks(1)
                _currentMonth.value = YearMonth.from(_selectedDate.value)
                loadDatesWithEvents()
                loadYandexEvents()
            }
            CalendarViewMode.DAY -> {
                _selectedDate.value = _selectedDate.value.minusDays(1)
                _currentMonth.value = YearMonth.from(_selectedDate.value)
                loadDatesWithEvents()
                loadYandexEvents()
            }
        }
    }

    fun navigateToNextMonth() {
        when (_viewMode.value) {
            CalendarViewMode.MONTH -> {
                _currentMonth.value = _currentMonth.value.plusMonths(1)
                loadDatesWithEvents()
                loadYandexEvents()
            }
            CalendarViewMode.WEEK -> {
                _selectedDate.value = _selectedDate.value.plusWeeks(1)
                _currentMonth.value = YearMonth.from(_selectedDate.value)
                loadDatesWithEvents()
                loadYandexEvents()
            }
            CalendarViewMode.DAY -> {
                _selectedDate.value = _selectedDate.value.plusDays(1)
                _currentMonth.value = YearMonth.from(_selectedDate.value)
                loadDatesWithEvents()
                loadYandexEvents()
            }
        }
    }

    fun setViewMode(mode: CalendarViewMode) {
        _viewMode.value = mode
        if (mode == CalendarViewMode.WEEK || mode == CalendarViewMode.DAY) {
            _currentMonth.value = YearMonth.from(_selectedDate.value)
        }
    }

    private fun loadDatesWithEvents() {
        viewModelScope.launch {
            val month = _currentMonth.value
            val dates = eventRepository.getDatesWithEvents(
                month.atDay(1).toString(),
                month.atEndOfMonth().toString()
            )
            _datesWithEvents.value = dates.toSet()
        }
    }
}

enum class CalendarViewMode { MONTH, WEEK, DAY }