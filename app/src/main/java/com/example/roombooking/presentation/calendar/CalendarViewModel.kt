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

    fun selectDate(date: LocalDate) { _selectedDate.value = date }

    fun navigateToPreviousMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
        loadDatesWithEvents()
    }

    fun navigateToNextMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
        loadDatesWithEvents()
    }

    fun setViewMode(mode: CalendarViewMode) { _viewMode.value = mode }

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
