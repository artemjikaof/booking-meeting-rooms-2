package com.example.roombooking.presentation.rooms

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roombooking.R
import com.example.roombooking.databinding.FragmentRoomDetailBinding
import com.example.roombooking.domain.model.Room
import com.example.roombooking.presentation.events.UiState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoomDetailFragment : Fragment() {

    private var _binding: FragmentRoomDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoomsViewModel by viewModels()
    private val roomId: Long by lazy { arguments?.getLong("roomId", -1L) ?: -1L }
    private var currentRoom: Room? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRoomDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        binding.btnEdit.setOnClickListener {
            val bundle = Bundle().apply { putLong("roomId", roomId) }
            findNavController().navigate(R.id.addEditRoomFragment, bundle)
        }

        binding.btnDelete.setOnClickListener {
            val room = currentRoom ?: return@setOnClickListener
            confirmDelete(room)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.rooms.collect { rooms ->
                val room = rooms.find { it.id == roomId } ?: return@collect
                currentRoom = room
                binding.toolbar.title = room.name
                binding.tvName.text = room.name
                binding.tvCapacity.text = "Вместимость: ${room.capacity} чел."
                binding.tvDescription.text = room.description.ifBlank { "Описание не указано" }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                        viewModel.resetState()
                    }
                    is UiState.Success -> {
                        viewModel.resetState()
                        findNavController().navigateUp()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun confirmDelete(room: Room) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Удалить помещение?")
            .setMessage("«${room.name}» будет удалено безвозвратно.")
            .setPositiveButton("Удалить") { _, _ -> viewModel.deleteRoom(room) }
            .setNegativeButton("Отмена", null)
            .show()
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
