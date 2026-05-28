package com.example.roombooking.presentation.rooms

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roombooking.R
import com.example.roombooking.databinding.FragmentRoomsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoomsFragment : Fragment() {

    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RoomsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RoomAdapter { room ->
            val bundle = android.os.Bundle().apply { putLong("roomId", room.id) }
            findNavController().navigate(R.id.roomDetailFragment, bundle)
        }
        binding.rvRooms.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRooms.adapter = adapter
        binding.fabAddRoom.setOnClickListener {
            findNavController().navigate(R.id.addEditRoomFragment)
        }
        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) { viewModel.setSearchQuery(s.toString()) }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.rooms.collect { adapter.submitList(it) }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
