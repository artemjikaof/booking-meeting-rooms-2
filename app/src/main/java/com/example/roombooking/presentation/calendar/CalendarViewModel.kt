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
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate.asStateFlow()

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth: StateFlow<YearMonth> = _currentMonth.asStateFlow()

    val eventsForSelectedDate: StateFlow<List<Event>> = _selectedDate
        .flatMapLatest { date -> eventRepository.getEventsByDate(date.toString()) }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _datesWithEvents = MutableStateFlow<Set<String>>(emptySet())
    val datesWithEvents: StateFlow<Set<String>> = _datesWithEvents.asStateFlow()

    private val _viewMode = MutableStateFlow(CalendarViewMode.MONTH)
    val viewMode: StateFlow<CalendarViewMode> = _viewMode.asStateFlow()

    init { loadDatesWithEvents() }

    fun selectDate(date: LocalDate) { 
        _selectedDate.value = date 
        // Если выбранная дата в другом месяце, переключаем текущий месяц
        val monthOfDate = YearMonth.from(date)
        if (_currentMonth.value != monthOfDate) {
            _currentMonth.value = monthOfDate
            loadDatesWithEvents()
        }
    }

    fun selectToday() {
        _selectedDate.value = LocalDate.now()
        _currentMonth.value = YearMonth.now()
        loadDatesWithEvents()
    }

    fun navigateToPreviousMonth() {
        when (_viewMode.value) {
            CalendarViewMode.MONTH -> {
                _currentMonth.value = _currentMonth.value.minusMonths(1)
                loadDatesWithEvents()
            }
            CalendarViewMode.WEEK -> {
                _selectedDate.value = _selectedDate.value.minusWeeks(1)
                _currentMonth.value = YearMonth.from(_selectedDate.value)
                loadDatesWithEvents()
            }
            CalendarViewMode.DAY -> {
                _selectedDate.value = _selectedDate.value.minusDays(1)
                _currentMonth.value = YearMonth.from(_selectedDate.value)
                loadDatesWithEvents()
            }
        }
    }

    fun navigateToNextMonth() {
        when (_viewMode.value) {
            CalendarViewMode.MONTH -> {
                _currentMonth.value = _currentMonth.value.plusMonths(1)
                loadDatesWithEvents()
            }
            CalendarViewMode.WEEK -> {
                _selectedDate.value = _selectedDate.value.plusWeeks(1)
                _currentMonth.value = YearMonth.from(_selectedDate.value)
                loadDatesWithEvents()
            }
            CalendarViewMode.DAY -> {
                _selectedDate.value = _selectedDate.value.plusDays(1)
                _currentMonth.value = YearMonth.from(_selectedDate.value)
                loadDatesWithEvents()
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
