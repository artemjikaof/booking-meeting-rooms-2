package com.example.roombooking.presentation.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roombooking.R
import com.example.roombooking.databinding.ItemEventBinding
import com.example.roombooking.domain.model.Event
import java.time.format.DateTimeFormatter
import java.util.Locale

class EventsAdapter(
    private val onClick: (Event) -> Unit
) : ListAdapter<Event, EventsAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EventViewHolder(
        private val binding: ItemEventBinding,
        private val onClick: (Event) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale("ru"))

        fun bind(event: Event) {
            binding.tvTitle.text = event.title
            binding.tvRoom.text = event.roomName
            binding.tvTime.text = "${event.timeStart.format(timeFormatter)} – ${event.timeEnd.format(timeFormatter)}"

            // Цвет полосы статуса
            val colorRes = when {
                event.isPast -> R.color.event_past
                event.isToday -> R.color.event_today
                else -> R.color.event_upcoming
            }
            binding.colorBar.setBackgroundColor(
                ContextCompat.getColor(binding.root.context, colorRes)
            )

            // Бейдж "Из системного календаря"
            binding.badgeFromCalendar.visibility =
                if (event.fromDeviceCalendar) android.view.View.VISIBLE else android.view.View.GONE

            // Бейдж статуса
            val (badgeText, badgeColorRes) = when {
                event.isPast -> Pair("Прошедшее", R.color.badge_past_bg)
                event.isToday -> Pair("Сегодня", R.color.badge_today_bg)
                else -> Pair("Предстоящее", R.color.badge_upcoming_bg)
            }
            binding.tvStatusBadge.text = badgeText
            binding.tvStatusBadge.backgroundTintList =
                ContextCompat.getColorStateList(binding.root.context, badgeColorRes)

            binding.root.setOnClickListener { onClick(event) }
        }
    }
}

class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Event, newItem: Event) = oldItem == newItem
}
