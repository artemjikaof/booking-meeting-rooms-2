package com.example.roombooking.presentation.calendar

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
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
    private lateinit var calendarAdapter: CalendarGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupWeekdaysRow()
        setupCalendarGrid()
        setupRecyclerView()
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
                    R.id.action_filter -> { findNavController().navigate(R.id.filterFragment); true }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupWeekdaysRow() {
        val days = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
        val row = binding.weekdaysRow
        row.removeAllViews()
        days.forEach { day ->
            val tv = TextView(requireContext()).apply {
                text = day
                textSize = 11f
                gravity = android.view.Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
                setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            }
            row.addView(tv)
        }
    }

    private fun setupCalendarGrid() {
        calendarAdapter = CalendarGridAdapter(
            requireContext(),
            viewModel.selectedDate.value,
            viewModel.currentMonth.value,
            viewModel.datesWithEvents.value
        ) { date ->
            viewModel.selectDate(date)
        }
        binding.gridCalendar.adapter = calendarAdapter
    }

    private fun setupRecyclerView() {
        eventsAdapter = EventsAdapter { event -> openEventDetail(event) }
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = eventsAdapter
    }

    private fun setupNavigation() {
        binding.btnPrevMonth.setOnClickListener { viewModel.navigateToPreviousMonth() }
        binding.btnNextMonth.setOnClickListener { viewModel.navigateToNextMonth() }
        binding.fabAddEvent.setOnClickListener {
            findNavController().navigate(R.id.addEditEventFragment)
        }
        binding.tabMonth.setOnClickListener { viewModel.setViewMode(CalendarViewMode.MONTH) }
        binding.tabWeek.setOnClickListener { viewModel.setViewMode(CalendarViewMode.WEEK) }
        binding.tabDay.setOnClickListener { viewModel.setViewMode(CalendarViewMode.DAY) }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedDate.collect { date ->
                refreshCalendar()
                binding.tvEventsHeader.text =
                    if (date == LocalDate.now()) "Сегодня"
                    else date.format(java.time.format.DateTimeFormatter.ofPattern("d MMMM", Locale("ru")))
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentMonth.collect { month ->
                val title = month.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale("ru"))
                    .replaceFirstChar { it.uppercase() } + " ${month.year}"
                binding.tvMonthTitle.text = title
                refreshCalendar()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventsForSelectedDate.collect { events ->
                eventsAdapter.submitList(events)
                binding.emptyState.visibility = if (events.isEmpty()) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.datesWithEvents.collect { refreshCalendar() }
        }
    }

    private fun refreshCalendar() {
        calendarAdapter.update(
            viewModel.selectedDate.value,
            viewModel.currentMonth.value,
            viewModel.datesWithEvents.value
        )
    }

    private fun openEventDetail(event: Event) {
        val bundle = android.os.Bundle().apply { putLong("eventId", event.id) }
        findNavController().navigate(R.id.eventDetailFragment, bundle)
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
