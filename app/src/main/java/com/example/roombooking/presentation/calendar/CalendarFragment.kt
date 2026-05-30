package com.example.roombooking.presentation.calendar

import android.graphics.Color
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

    private val handler = android.os.Handler(android.os.Looper.getMainLooper())
    private val updateTimeRunnable = object : Runnable {
        override fun run() {
            if (viewModel.viewMode.value == CalendarViewMode.DAY) {
                updateRedLine()
            }
            handler.postDelayed(this, 60000)
        }
    }

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
        handler.post(updateTimeRunnable)
    }

    private fun setupMenu() {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, inflater: MenuInflater) {
                inflater.inflate(R.menu.menu_calendar, menu)
            }
            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return false
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
                textSize = 12f
                gravity = android.view.Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
                setTextColor(Color.parseColor("#757575"))
            }
            row.addView(tv)
        }
    }

    private fun setupCalendarGrid() {
        calendarAdapter = CalendarGridAdapter(
            requireContext(),
            viewModel.selectedDate.value,
            viewModel.currentMonth.value,
            viewModel.datesWithEvents.value,
            viewModel.viewMode.value
        ) { date ->
            if (viewModel.selectedDate.value == date && viewModel.viewMode.value != CalendarViewMode.DAY) {
                viewModel.setViewMode(CalendarViewMode.DAY)
            } else {
                viewModel.selectDate(date)
            }
        }
        binding.gridCalendar.adapter = calendarAdapter
        setupDayView()
    }

    private fun setupDayView() {
        binding.containerHours.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        for (i in 0..23) {
            val hourView = inflater.inflate(R.layout.item_hour, binding.containerHours, false)
            hourView.findViewById<TextView>(R.id.tv_hour).text = String.format("%02d:00", i)
            hourView.setOnClickListener {
                val dateStr = viewModel.selectedDate.value.toString()
                val timeStart = String.format("%02d:00", i)
                val bundle = Bundle().apply {
                    putString("initialDate", dateStr)
                    putString("initialTime", timeStart)
                }
                findNavController().navigate(R.id.action_calendar_to_addEvent, bundle)
            }
            binding.containerHours.addView(hourView)
        }
    }

    private fun updateRedLine() {
        val now = java.time.LocalTime.now()
        val hour = now.hour
        val minute = now.minute
        
        val density = resources.displayMetrics.density
        val hourHeightDp = 60
        val yDp = (hour * hourHeightDp) + (minute * hourHeightDp / 60f)
        val yPx = yDp * density
        
        // Смещаем линию так, чтобы она совпадала с новой позицией линий в item_hour (которые теперь сдвинуты на 10dp вниз)
        binding.viewCurrentTimeIndicator.translationY = yPx
        binding.tvCurrentTimeLabel.text = String.format("%02d:%02d", hour, minute)
        binding.viewCurrentTimeIndicator.visibility = if (viewModel.selectedDate.value == LocalDate.now()) View.VISIBLE else View.GONE
        
        // Выводим индикатор на передний план, чтобы перекрывал всё
        binding.viewCurrentTimeIndicator.bringToFront()
    }

    private fun renderEventsOnSchedule(events: List<Event>) {
        val layout = binding.layoutDaySchedule
        val viewsToRemove = mutableListOf<View>()
        for (i in 0 until layout.childCount) {
            val child = layout.getChildAt(i)
            if (child.id != R.id.container_hours && child.id != R.id.view_current_time_indicator) {
                viewsToRemove.add(child)
            }
        }
        viewsToRemove.forEach { layout.removeView(it) }

        val density = resources.displayMetrics.density
        val hourHeightDp = 60
        val leftMarginPx = (50 * density).toInt()

        // Сортируем по времени начала
        val sortedEvents = events.sortedBy { it.timeStart }

        sortedEvents.forEach { event ->
            try {
                val start = java.time.LocalTime.parse(event.timeStart)
                val end = java.time.LocalTime.parse(event.timeEnd)
                
                val startMinutes = start.hour * 60 + start.minute
                val endMinutes = end.hour * 60 + end.minute
                val duration = endMinutes - startMinutes
                
                if (duration <= 0) return@forEach

                // Рассчитываем позицию (учитываем смещение линии в 10dp)
                val topPx = ((startMinutes * hourHeightDp / 60f + 10) * density).toInt()
                // Уменьшаем высоту на 2px для зазора между событиями
                val heightPx = (duration * hourHeightDp / 60f * density).toInt() - 2

                val eventView = LayoutInflater.from(requireContext()).inflate(R.layout.item_event_rect, layout, false)
                eventView.findViewById<TextView>(R.id.tv_event_title).text = event.title
                
                val params = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    if (heightPx > 0) heightPx else 1
                ).apply {
                    topMargin = topPx + 1 // Смещение на 1px для зазора сверху
                    leftMargin = leftMarginPx
                    rightMargin = (8 * density).toInt()
                }
                
                eventView.layoutParams = params
                eventView.setOnClickListener { openEventDetail(event) }
                layout.addView(eventView)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // После отрисовки событий снова поднимаем индикатор времени наверх
        binding.viewCurrentTimeIndicator.bringToFront()
    }

    private fun setupRecyclerView() {
        eventsAdapter = EventsAdapter { event -> openEventDetail(event) }
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = eventsAdapter
    }

    private fun setupNavigation() {
        binding.btnPrevMonth.setOnClickListener { viewModel.navigateToPreviousMonth() }
        binding.btnNextMonth.setOnClickListener { viewModel.navigateToNextMonth() }
        
        binding.btnPrevDay.setOnClickListener { viewModel.navigateToPreviousMonth() }
        binding.btnNextDay.setOnClickListener { viewModel.navigateToNextMonth() }
        
        binding.toggleGroupView.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.tab_month -> viewModel.setViewMode(CalendarViewMode.MONTH)
                    R.id.tab_week -> viewModel.setViewMode(CalendarViewMode.WEEK)
                    R.id.tab_day -> viewModel.setViewMode(CalendarViewMode.DAY)
                }
            }
        }

        binding.fabAddEvent.setOnClickListener {
            val dateStr = viewModel.selectedDate.value.toString()
            val bundle = android.os.Bundle().apply {
                putString("initialDate", dateStr)
            }
            findNavController().navigate(R.id.action_calendar_to_addEvent, bundle)
        }

        binding.btnTodayFloating.setOnClickListener {
            viewModel.selectToday()
        }

        binding.tvDayViewTitle.setOnClickListener {
            viewModel.setViewMode(CalendarViewMode.MONTH)
            binding.toggleGroupView.check(R.id.tab_month)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewMode.collect { mode ->
                val checkedId = when (mode) {
                    CalendarViewMode.MONTH -> R.id.tab_month
                    CalendarViewMode.WEEK -> R.id.tab_week
                    CalendarViewMode.DAY -> R.id.tab_day
                }
                if (binding.toggleGroupView.checkedButtonId != checkedId) {
                    binding.toggleGroupView.check(checkedId)
                }

                when (mode) {
                    CalendarViewMode.MONTH, CalendarViewMode.WEEK -> {
                        binding.containerMonthWeek.visibility = View.VISIBLE
                        binding.scrollViewHourly.visibility = View.GONE
                        binding.layoutDayTitleNav.visibility = View.GONE
                        binding.layoutNavMonth.visibility = View.VISIBLE
                        refreshCalendar()
                    }
                    CalendarViewMode.DAY -> {
                        binding.containerMonthWeek.visibility = View.GONE
                        binding.scrollViewHourly.visibility = View.VISIBLE
                        binding.layoutDayTitleNav.visibility = View.VISIBLE
                        binding.layoutNavMonth.visibility = View.GONE
                        updateDayTitle()
                        updateRedLine()
                        renderEventsOnSchedule(viewModel.eventsForSelectedDate.value)
                        scrollToCurrentTime()
                    }
                }
                updateTodayButtonVisibility(viewMode = mode)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedDate.collect { date ->
                refreshCalendar()
                updateDayTitle()
                updateTodayButtonVisibility(selectedDate = date)
                binding.tvEventsHeader.text =
                    if (date == LocalDate.now()) "Сегодня"
                    else date.format(java.time.format.DateTimeFormatter.ofPattern("d MMMM", Locale("ru")))
                
                if (viewModel.viewMode.value == CalendarViewMode.DAY) {
                    updateRedLine()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentMonth.collect { month ->
                val title = month.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale("ru"))
                    .replaceFirstChar { it.uppercase() } + " ${month.year}"
                binding.tvMonthTitle.text = title
                refreshCalendar()
                updateTodayButtonVisibility(currentMonth = month)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.eventsForSelectedDate.collect { events ->
                eventsAdapter.submitList(events)
                binding.emptyState.visibility = if (events.isEmpty()) View.VISIBLE else View.GONE
                if (viewModel.viewMode.value == CalendarViewMode.DAY) {
                    renderEventsOnSchedule(events)
                }
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
            viewModel.datesWithEvents.value,
            viewModel.viewMode.value
        )
    }

    private fun updateDayTitle() {
        val date = viewModel.selectedDate.value
        val dayTitle = date.format(java.time.format.DateTimeFormatter.ofPattern("d MMMM, EEEE", Locale("ru")))
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale("ru")) else it.toString() }
        binding.tvDayViewTitle.text = dayTitle
    }

    private fun scrollToCurrentTime() {
        val now = java.time.LocalTime.now()
        val hour = now.hour
        val density = resources.displayMetrics.density
        val hourHeightDp = 60
        // Прокручиваем так, чтобы текущий час был в верхней части экрана (с небольшим отступом)
        val scrollY = ((hour * hourHeightDp) * density).toInt()
        
        binding.scrollViewHourly.post {
            binding.scrollViewHourly.smoothScrollTo(0, scrollY)
        }
    }

    private fun updateTodayButtonVisibility(
        selectedDate: LocalDate = viewModel.selectedDate.value,
        currentMonth: YearMonth = viewModel.currentMonth.value,
        viewMode: CalendarViewMode = viewModel.viewMode.value
    ) {
        val isTodayDate = selectedDate == LocalDate.now()
        val isCurrentMonth = currentMonth == YearMonth.now()
        
        val shouldShow = !isTodayDate || (!isCurrentMonth && viewMode == CalendarViewMode.MONTH)
        
        val density = resources.displayMetrics.density
        val targetY = if (shouldShow) 0f else 150 * density
        val targetAlpha = if (shouldShow) 1f else 0f
        
        binding.btnTodayFloating.animate()
            .translationY(targetY)
            .alpha(targetAlpha)
            .setDuration(300)
            .start()
        
        binding.btnTodayFloating.isClickable = shouldShow
    }

    private fun openEventDetail(event: Event) {
        val bundle = android.os.Bundle().apply { putLong("eventId", event.id) }
        findNavController().navigate(R.id.eventDetailFragment, bundle)
    }

    override fun onDestroyView() { 
        super.onDestroyView()
        handler.removeCallbacks(updateTimeRunnable)
        _binding = null 
    }
}
