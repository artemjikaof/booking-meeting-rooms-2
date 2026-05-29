package com.example.roombooking.presentation.calendar

import android.content.Context
import android.graphics.Color
import android.view.*
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.roombooking.R
import java.time.LocalDate
import java.time.YearMonth

class CalendarGridAdapter(
    private val context: Context,
    private var selectedDate: LocalDate,
    private var currentMonth: YearMonth,
    private var datesWithEvents: Set<String>,
    private val onDateClick: (LocalDate) -> Unit
) : BaseAdapter() {

    private var days: List<LocalDate?> = buildDays()

    fun update(selected: LocalDate, month: YearMonth, events: Set<String>) {
        selectedDate = selected
        currentMonth = month
        datesWithEvents = events
        days = buildDays()
        notifyDataSetChanged()
    }

    private fun buildDays(): List<LocalDate?> {
        val firstDay = currentMonth.atDay(1)
        // Понедельник = 1, сдвиг: Mon=0 offset
        val dayOfWeek = firstDay.dayOfWeek.value - 1 // Mon=0..Sun=6
        val daysInMonth = currentMonth.lengthOfMonth()
        val cells = mutableListOf<LocalDate?>()
        // Пустые ячейки до первого дня
        repeat(dayOfWeek) { cells.add(null) }
        // Дни месяца
        for (d in 1..daysInMonth) cells.add(currentMonth.atDay(d))
        // Добить до кратного 7
        while (cells.size % 7 != 0) cells.add(null)
        return cells
    }

    override fun getCount() = days.size
    override fun getItem(pos: Int) = days[pos]
    override fun getItemId(pos: Int) = pos.toLong()

    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val root = convertView as? FrameLayout
            ?: LayoutInflater.from(context).inflate(R.layout.item_calendar_day, parent, false) as FrameLayout

        val tvDay = root.findViewById<TextView>(R.id.tv_day)
        val dot = root.findViewById<View>(R.id.view_dot)
        val date = days[pos]

        if (date == null) {
            tvDay.text = ""
            tvDay.background = null
            dot.visibility = View.GONE
            root.setOnClickListener(null)
            return root
        }

        tvDay.text = date.dayOfMonth.toString()
        val today = LocalDate.now()
        val isToday = date == today
        val isSelected = date == selectedDate
        val hasEvent = datesWithEvents.contains(date.toString())

        when {
            isToday -> {
                tvDay.setBackgroundResource(R.drawable.bg_day_today)
                tvDay.setTextColor(Color.WHITE)
            }
            isSelected -> {
                tvDay.setBackgroundResource(R.drawable.bg_day_selected)
                tvDay.setTextColor(ContextCompat.getColor(context, R.color.md_blue_700))
            }
            else -> {
                tvDay.background = null
                tvDay.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
            }
        }

        dot.visibility = if (hasEvent) View.VISIBLE else View.GONE
        root.setOnClickListener { onDateClick(date) }
        return root
    }
}
