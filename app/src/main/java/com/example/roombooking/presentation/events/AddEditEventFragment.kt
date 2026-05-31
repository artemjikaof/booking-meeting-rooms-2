package com.example.roombooking.presentation.events

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roombooking.databinding.FragmentAddEditEventBinding
import com.example.roombooking.domain.model.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@AndroidEntryPoint
class AddEditEventFragment : Fragment() {

    private var _binding: FragmentAddEditEventBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddEditEventViewModel by viewModels()

    private val eventId: Long by lazy { arguments?.getLong("eventId", -1L) ?: -1L }
    private val isEditing get() = eventId > 0L

    private var selectedDateStart = LocalDate.now()
    private var selectedDateEnd = LocalDate.now()
    private var selectedTimeStart = LocalTime.of(9, 0)
    private var selectedTimeEnd = LocalTime.of(10, 0)
    private var selectedRoom: Room? = null
    private var roomsList: List<Room> = emptyList()
    private var formFilledFromEvent = false

    private val dateFmt = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru"))
    private val timeFmt = DateTimeFormatter.ofPattern("HH:mm", Locale("ru"))
    private val isoDate = DateTimeFormatter.ISO_LOCAL_DATE
    private val isoTime = DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Заголовок ActionBar
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            if (isEditing) "Редактировать мероприятие" else "Новое мероприятие"

        if (isEditing) viewModel.loadEvent(eventId)

        updateDisplays()
        setupClicks()
        observeViewModel()
    }

    private fun setupClicks() {
        binding.btnDateStart.setOnClickListener { pickDate(isStart = true) }
        binding.btnDateEnd.setOnClickListener { pickDate(isStart = false) }
        binding.btnTimeStart.setOnClickListener { pickTime(isStart = true) }
        binding.btnTimeEnd.setOnClickListener { pickTime(isStart = false) }
        binding.btnSave.setOnClickListener { save() }
        binding.btnCancel.setOnClickListener { findNavController().navigateUp() }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.rooms.collect { rooms ->
                roomsList = rooms
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    rooms.map { it.name }
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerRoom.adapter = adapter
                binding.spinnerRoom.onItemSelectedListener =
                    object : android.widget.AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p: android.widget.AdapterView<*>?, v: View?, pos: Int, id: Long) {
                            selectedRoom = rooms.getOrNull(pos)
                        }
                        override fun onNothingSelected(p: android.widget.AdapterView<*>?) {}
                    }
                val event = viewModel.currentEvent.value
                if (event != null && !formFilledFromEvent) fillFormFromEvent(rooms)
                else if (selectedRoom == null) selectedRoom = rooms.firstOrNull()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentEvent.collect { event ->
                event ?: return@collect
                if (!formFilledFromEvent) fillFormFromEvent(roomsList)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Success -> findNavController().navigateUp()
                    is UiState.Error -> Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    is UiState.ConflictDetected -> showConflict(state.roomName, state.time)
                    else -> Unit
                }
            }
        }
    }

    private fun fillFormFromEvent(rooms: List<Room>) {
        val event = viewModel.currentEvent.value ?: return
        if (rooms.isEmpty()) return
        formFilledFromEvent = true
        binding.etTitle.setText(event.title)
        binding.etDescription.setText(event.description)
        binding.etParticipants.setText(event.participants)
        binding.switchSyncCalendar.isChecked = event.syncToDeviceCalendar
        selectedDateStart = LocalDate.parse(event.dateStart)
        selectedDateEnd = LocalDate.parse(event.dateEnd)
        selectedTimeStart = LocalTime.parse(event.timeStart)
        selectedTimeEnd = LocalTime.parse(event.timeEnd)
        updateDisplays()
        val roomIndex = rooms.indexOfFirst { it.id == event.roomId }
        if (roomIndex >= 0) {
            binding.spinnerRoom.setSelection(roomIndex)
            selectedRoom = rooms[roomIndex]
        }
    }

    private fun save() {
        val room = selectedRoom ?: run {
            Toast.makeText(requireContext(), "Выберите помещение", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.saveEvent(
            title = binding.etTitle.text.toString().trim(),
            dateStart = selectedDateStart.format(isoDate),
            dateEnd = selectedDateEnd.format(isoDate),
            timeStart = selectedTimeStart.format(isoTime),
            timeEnd = selectedTimeEnd.format(isoTime),
            room = room,
            description = binding.etDescription.text.toString().trim(),
            participants = binding.etParticipants.text.toString().trim(),
            syncToCalendar = binding.switchSyncCalendar.isChecked
        )
    }

    private fun pickDate(isStart: Boolean) {
        val d = if (isStart) selectedDateStart else selectedDateEnd
        DatePickerDialog(requireContext(), { _, y, m, day ->
            val picked = LocalDate.of(y, m + 1, day)
            if (isStart) { selectedDateStart = picked; if (picked > selectedDateEnd) selectedDateEnd = picked }
            else selectedDateEnd = picked
            updateDisplays()
        }, d.year, d.monthValue - 1, d.dayOfMonth).show()
    }

    private fun pickTime(isStart: Boolean) {
        val t = if (isStart) selectedTimeStart else selectedTimeEnd
        TimePickerDialog(requireContext(), { _, h, min ->
            if (isStart) selectedTimeStart = LocalTime.of(h, min)
            else selectedTimeEnd = LocalTime.of(h, min)
            updateDisplays()
        }, t.hour, t.minute, true).show()
    }

    private fun updateDisplays() {
        binding.btnDateStart.text = selectedDateStart.format(dateFmt)
        binding.btnDateEnd.text = selectedDateEnd.format(dateFmt)
        binding.btnTimeStart.text = selectedTimeStart.format(timeFmt)
        binding.btnTimeEnd.text = selectedTimeEnd.format(timeFmt)
    }

    private fun showConflict(room: String, time: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Помещение занято")
            .setMessage("«$room» уже забронировано на $time.")
            .setPositiveButton("Изменить", null)
            .show()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
