package com.example.roombooking.presentation.rooms

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roombooking.databinding.FragmentRoomDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoomDetailFragment : Fragment() {

    private var _binding: FragmentRoomDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoomsViewModel by viewModels()
    private var roomId: Long = -1L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRoomDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomId = arguments?.getLong("roomId", -1L) ?: -1L

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.btnDelete.setOnClickListener { confirmDelete() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.rooms.collect { rooms ->
                val room = rooms.find { it.id == roomId } ?: return@collect
                binding.tvName.text = room.name
                binding.tvCapacity.text = "Вместимость: ${room.capacity} чел."
                binding.tvDescription.text = room.description.ifBlank { "—" }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (state is com.example.roombooking.presentation.events.UiState.Error)
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                if (state is com.example.roombooking.presentation.events.UiState.Success)
                    findNavController().navigateUp()
            }
        }
    }

    private fun confirmDelete() {
        val room = viewModel.rooms.value.find { it.id == roomId } ?: return
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Удалить помещение?")
            .setMessage("Все мероприятия в «${room.name}» будут отвязаны.")
            .setPositiveButton("Удалить") { _, _ -> viewModel.deleteRoom(room) }
            .setNegativeButton("Отмена", null)
            .show()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
