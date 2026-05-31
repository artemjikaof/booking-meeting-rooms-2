package com.example.roombooking.presentation.rooms

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roombooking.databinding.FragmentAddEditRoomBinding
import com.example.roombooking.presentation.events.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditRoomFragment : Fragment() {

    private var _binding: FragmentAddEditRoomBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoomsViewModel by viewModels()

    private val roomId: Long by lazy { arguments?.getLong("roomId", -1L) ?: -1L }
    private val isEditing get() = roomId > 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title =
            if (isEditing) "Редактировать помещение" else "Новое помещение"

        if (isEditing) viewModel.loadRoom(roomId)

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val cap = binding.etCapacity.text.toString().toIntOrNull() ?: 0
            val desc = binding.etDescription.text.toString().trim()
            viewModel.saveRoom(name, cap, desc)
        }
        binding.btnCancel.setOnClickListener { findNavController().navigateUp() }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editingRoom.collect { room ->
                room ?: return@collect
                binding.etName.setText(room.name)
                binding.etCapacity.setText(room.capacity.toString())
                binding.etDescription.setText(room.description)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        viewModel.resetState()
                        findNavController().navigateUp()
                    }
                    is UiState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                        viewModel.resetState()
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
