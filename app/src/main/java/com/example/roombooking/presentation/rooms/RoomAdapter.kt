package com.example.roombooking.presentation.rooms

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roombooking.databinding.ItemRoomBinding
import com.example.roombooking.domain.model.Room

class RoomAdapter(private val onClick: (Room) -> Unit) :
    ListAdapter<Room, RoomAdapter.VH>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false), onClick)

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    class VH(private val b: ItemRoomBinding, private val onClick: (Room) -> Unit) :
        RecyclerView.ViewHolder(b.root) {
        fun bind(room: Room) {
            b.tvName.text = room.name
            b.tvCapacity.text = "Вместимость: ${room.capacity} чел."
            b.tvDescription.text = room.description
            b.root.setOnClickListener { onClick(room) }
        }
    }

    class Diff : DiffUtil.ItemCallback<Room>() {
        override fun areItemsTheSame(a: Room, b: Room) = a.id == b.id
        override fun areContentsTheSame(a: Room, b: Room) = a == b
    }
}
