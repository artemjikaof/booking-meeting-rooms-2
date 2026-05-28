package com.example.roombooking.presentation.rooms

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roombooking.databinding.FragmentAddEditRoomBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditRoomFragment : Fragment() {

    private var _binding: FragmentAddEditRoomBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoomsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddEditRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val cap = binding.etCapacity.text.toString().toIntOrNull() ?: 0
            val desc = binding.etDescription.text.toString()
            viewModel.saveRoom(name, cap, desc)
        }
        binding.btnCancel.setOnClickListener { findNavController().navigateUp() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is com.example.roombooking.presentation.events.UiState.Success -> findNavController().navigateUp()
                    is com.example.roombooking.presentation.events.UiState.Error ->
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
