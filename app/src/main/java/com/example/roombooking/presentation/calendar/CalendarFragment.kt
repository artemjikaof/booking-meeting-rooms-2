package com.example.roombooking.presentation.calendar

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roombooking.R
import com.example.roombooking.databinding.FragmentCalendarBinding
import com.example.roombooking.domain.model.Event
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@AndroidEntryPoint
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupCalendar()
        setupRecyclerView()
        setupViewModeTabs()
        setupNavigation()
        observeViewModel()
    }

    private fun setupMenu() {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
                inflater.inflate(R.menu.menu_calendar, menu)
            }
            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.action_filter -> {
                        findNavController().navigate(R.id.filterFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupCalendar() {
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(12)
        val endMonth = currentMonth.plusMonths(12)

        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view) { day ->
                if (day.position == DayPosition.MonthDate) viewModel.selectDate(day.date)
            }

            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.bind(
                    day = data,
                    isSelected = data.date == viewModel.selectedDate.value,
                    isToday = data.date == LocalDate.now(),
                    hasEvents = viewModel.datesWithEvents.value.contains(data.date.toString())
                )
            }
        }

        binding.calendarView.monthScrollListener = { month ->
            val title = month.yearMonth.month
                .getDisplayName(TextStyle.FULL_STANDALONE, Locale("ru"))
                .replaceFirstChar { it.uppercase() } + " ${month.yearMonth.year}"
            binding.tvMonthTitle.text = title
        }

        binding.calendarView.setup(startMonth, endMonth, firstDayOfWeekFromLocale())
        binding.calendarView.scrollToMonth(currentMonth)
    }

    private fun setupRecyclerView() {
        eventsAdapter = EventsAdapter { event -> openEventDetail(event) }
        binding.rvEvents.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventsAdapter
        }
    }

    private fun setupViewModeTabs() {
        binding.tabMonth.setOnClickListener { viewModel.setViewMode(CalendarViewMode.MONTH) }
        binding.tabWeek.setOnClickListener { viewModel.setViewMode(CalendarViewMode.WEEK) }
        binding.tabDay.setOnClickListener { viewModel.setViewMode(CalendarViewMode.DAY) }
    }

    private fun setupNavigation() {
        binding.btnPrevMonth.setOnClickListener { viewModel.navigateToPreviousMonth() }
        binding.btnNextMonth.setOnClickListener { viewModel.navigateToNextMonth() }
        binding.fabAddEvent.setOnClickListener {
            findNavController().navigate(R.id.addEditEventFragment)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventsForSelectedDate.collect { events ->
                eventsAdapter.submitList(events)
                binding.tvEventsHeader.text =
                    if (viewModel.selectedDate.value == LocalDate.now()) "Сегодня"
                    else viewModel.selectedDate.value.toString()
                binding.emptyState.visibility =
                    if (events.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.datesWithEvents.collect {
                binding.calendarView.notifyCalendarChanged()
            }
        }
    }

    private fun openEventDetail(event: Event) {
        val bundle = android.os.Bundle().apply { putLong("eventId", event.id) }
        findNavController().navigate(R.id.eventDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class DayViewContainer(
    view: View,
    private val onClick: (CalendarDay) -> Unit
) : ViewContainer(view) {

    // Используем findViewById напрямую — без ViewBinding для избежания NPE
    private val tvDay: TextView = view.findViewById(R.id.tv_day)
    private val viewDot: View = view.findViewById(R.id.view_dot)
    lateinit var day: CalendarDay

    init {
        view.setOnClickListener { onClick(day) }
    }

    fun bind(day: CalendarDay, isSelected: Boolean, isToday: Boolean, hasEvents: Boolean) {
        this.day = day
        tvDay.text = day.date.dayOfMonth.toString()

        // Цвет текста в зависимости от позиции
        val textAlpha = if (day.position == DayPosition.MonthDate) 1f else 0.3f
        tvDay.alpha = textAlpha

        // Фон дня
        when {
            isToday -> {
                tvDay.setBackgroundResource(R.drawable.bg_day_today)
                tvDay.setTextColor(android.graphics.Color.WHITE)
            }
            isSelected -> {
                tvDay.setBackgroundResource(R.drawable.bg_day_selected)
                tvDay.setTextColor(tvDay.context.getColor(R.color.md_blue_700))
            }
            else -> {
                tvDay.background = null
                tvDay.setTextColor(tvDay.context.getColor(android.R.color.darker_gray))
            }
        }

        viewDot.visibility = if (hasEvents && day.position == DayPosition.MonthDate)
            View.VISIBLE else View.GONE
    }
}
