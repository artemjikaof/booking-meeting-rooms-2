package com.example.roombooking.presentation.events

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roombooking.R
import com.example.roombooking.data.repository.EventRepository
import com.example.roombooking.databinding.FragmentEventsListBinding
import com.example.roombooking.domain.model.Event
import com.example.roombooking.presentation.calendar.EventsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EventsListFragment : Fragment() {

    private var _binding: FragmentEventsListBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var eventRepository: EventRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEventsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = EventsAdapter { event -> openDetail(event) }
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = adapter
        binding.fabAddEvent.setOnClickListener {
            findNavController().navigate(R.id.addEditEventFragment)
        }
        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun afterTextChanged(s: android.text.Editable?) {
                viewLifecycleOwner.lifecycleScope.launch {
                    eventRepository.searchEvents(s.toString()).collectLatest { adapter.submitList(it) }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        viewLifecycleOwner.lifecycleScope.launch {
            eventRepository.getAllEvents().collectLatest { adapter.submitList(it) }
        }
    }

    private fun openDetail(event: Event) {
        val bundle = android.os.Bundle().apply { putLong("eventId", event.id) }
        findNavController().navigate(R.id.eventDetailFragment, bundle)
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
