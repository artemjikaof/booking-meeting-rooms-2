package com.example.roombooking.presentation.events

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

    private var selectedDateStart = LocalDate.now()
    private var selectedDateEnd = LocalDate.now()
    private var selectedTimeStart = LocalTime.of(9, 0)
    private var selectedTimeEnd = LocalTime.of(10, 0)
    private var selectedRoom: Room? = null

    private val dateFmt = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru"))
    private val timeFmt = DateTimeFormatter.ofPattern("HH:mm", Locale("ru"))
    private val isoDate = DateTimeFormatter.ISO_LOCAL_DATE
    private val isoTime = DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddEditEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                val names = rooms.map { it.name }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, names)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerRoom.adapter = adapter
                binding.spinnerRoom.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p: android.widget.AdapterView<*>?, v: View?, pos: Int, id: Long) {
                        selectedRoom = rooms.getOrNull(pos)
                    }
                    override fun onNothingSelected(p: android.widget.AdapterView<*>?) {}
                }
                if (selectedRoom == null) selectedRoom = rooms.firstOrNull()
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

    private fun save() {
        val room = selectedRoom ?: run {
            Toast.makeText(requireContext(), "Выберите помещение", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.saveEvent(
            title = binding.etTitle.text.toString(),
            dateStart = selectedDateStart.format(isoDate),
            dateEnd = selectedDateEnd.format(isoDate),
            timeStart = selectedTimeStart.format(isoTime),
            timeEnd = selectedTimeEnd.format(isoTime),
            room = room,
            description = binding.etDescription.text.toString(),
            participants = binding.etParticipants.text.toString(),
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
