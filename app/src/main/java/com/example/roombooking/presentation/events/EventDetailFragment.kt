package com.example.roombooking.presentation.events

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roombooking.R
import com.example.roombooking.data.repository.EventRepository
import com.example.roombooking.databinding.FragmentEventDetailBinding
import com.example.roombooking.domain.model.Event
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EventDetailFragment : Fragment() {

    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var eventRepository: EventRepository
    private var currentEvent: Event? = null
    private val eventId: Long by lazy { arguments?.getLong("eventId", -1L) ?: -1L }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = "Мероприятие"

        binding.btnEdit.setOnClickListener {
            val bundle = Bundle().apply { putLong("eventId", eventId) }
            findNavController().navigate(R.id.addEditEventFragment, bundle)
        }
        binding.btnDelete.setOnClickListener { confirmDelete() }

        loadEvent()
    }

    private fun loadEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            val event = eventRepository.getEventById(eventId)
            if (event == null) {
                Toast.makeText(requireContext(), "Мероприятие не найдено", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
                return@launch
            }
            currentEvent = event
            bindEvent(event)
        }
    }

    private fun bindEvent(event: Event) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = event.title
        binding.tvTitle.text = event.title
        binding.tvRoom.text = if (event.roomName.isNotBlank()) "📍 ${event.roomName}" else "Помещение не указано"
        binding.tvDate.text = "📅 ${event.dateStart} – ${event.dateEnd}"
        binding.tvTime.text = "🕐 ${event.timeStart} – ${event.timeEnd}"
        binding.tvDescription.text = event.description.ifBlank { "Описание не указано" }
        binding.tvParticipants.text = if (event.participants.isNotBlank()) "👥 ${event.participants}" else "Участники не указаны"
        binding.tvSyncStatus.text = if (event.syncToDeviceCalendar) "✓ Добавлено в системный календарь" else "Не синхронизировано"
    }

    private fun confirmDelete() {
        val event = currentEvent ?: return
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Удалить мероприятие?")
            .setMessage("«${event.title}» будет удалено безвозвратно.")
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
