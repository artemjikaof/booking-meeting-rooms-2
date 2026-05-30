package com.example.roombooking.presentation.events

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roombooking.R
import com.example.roombooking.data.repository.EventRepository
import com.example.roombooking.databinding.FragmentEventDetailBinding
import com.example.roombooking.domain.model.Event
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EventDetailFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var eventRepository: EventRepository
    private var currentEvent: Event? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.let {
            val behavior = com.google.android.material.bottomsheet.BottomSheetBehavior.from(it)
            behavior.state = com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
        }

        val eventId = arguments?.getLong("eventId", -1L) ?: -1L
        binding.btnEdit.setOnClickListener {
            val bundle = android.os.Bundle().apply { putLong("eventId", eventId) }
            findNavController().navigate(R.id.addEditEventFragment, bundle)
        }
        binding.btnDelete.setOnClickListener { confirmDelete() }

        viewLifecycleOwner.lifecycleScope.launch {
            val event = eventRepository.getEventById(eventId) ?: return@launch
            currentEvent = event
            binding.tvTitle.text = event.title
            binding.tvRoom.text = event.roomName.ifBlank { "—" }
            binding.tvDate.text = "${event.dateStart} – ${event.dateEnd}"
            binding.tvTime.text = "${event.timeStart} – ${event.timeEnd}"
            binding.tvDescription.text = event.description.ifBlank { "—" }
            binding.tvParticipants.text = event.participants.ifBlank { "—" }
            binding.tvSyncStatus.text = if (event.syncToDeviceCalendar) "Добавлено в календарь" else "Не синхронизировано"
        }
    }

    private fun confirmDelete() {
        val event = currentEvent ?: return
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Удалить мероприятие?")
            .setPositiveButton("Удалить") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    eventRepository.deleteEvent(event)
                    findNavController().navigateUp()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
