package com.example.roombooking.presentation.calendar

import androidx.lifecycle.*
import com.example.roombooking.data.repository.EventRepository
import com.example.roombooking.domain.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val eventRepository: com.example.roombooking.data.repository.EventRepository,
    private val yandexRepository: com.example.roombooking.data.repository.YandexCalendarRepository
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

    private fun loadYandexEvents() {
        if (!yandexRepository.isAuthorized()) return
        viewModelScope.launch {
            val month = _currentMonth.value
            val result = yandexRepository.getYandexEvents(
                from = "${month.atDay(1)}T00:00:00+03:00",
                to = "${month.atEndOfMonth()}T23:59:59+03:00"
            )
            result.onSuccess { yandexList ->
                _yandexEvents.value = yandexList.map { dto ->
                    // Маппим DTO Яндекса в нашу доменную модель Event (ТЗ 2.5)
                    Event(
                        title = "[Яндекс] ${dto.summary}",
                        dateStart = dto.start.dateTime.substring(0, 10),
                        dateEnd = dto.end.dateTime.substring(0, 10),
                        timeStart = dto.start.dateTime.substring(11, 16),
                        timeEnd = dto.end.dateTime.substring(11, 16),
                        roomId = -1, // Специальный ID для внешних событий
                        roomName = dto.location ?: "Яндекс Календарь",
                        description = dto.description ?: "",
                        fromDeviceCalendar = true // Используем этот флаг для индикации внешнего источника
                    )
                }
            }
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
            // Убеждаемся, что текущий месяц соответствует выбранной дате
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
