package com.example.roombooking.presentation.settings

import android.Manifest
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.roombooking.data.repository.SyncConflictData
import com.example.roombooking.databinding.FragmentSettingsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    private val calendarPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.all { it }
        if (granted) {
            viewModel.setSyncEnabled(true)
            viewModel.loadCalendars()
            showCalendarChooser()
        } else {
            Toast.makeText(requireContext(), "Доступ к календарю не предоставлен", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }

    // ИСПРАВЛЕНО: обновляем статус Яндекс при каждом возврате на экран
    // (авторизация происходит в браузере → MainActivity → сюда)
    override fun onResume() {
        super.onResume()
        viewModel.refreshYandexStatus()
    }

    private fun setupViews() {
        binding.switchSync.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && !viewModel.hasCalendarPermission) {
                binding.switchSync.isChecked = false
                showPermissionRationale()
            } else {
                viewModel.setSyncEnabled(isChecked)
            }
        }

        binding.btnSelectCalendar.setOnClickListener {
            if (viewModel.hasCalendarPermission) {
                viewModel.loadCalendars()
                showCalendarChooser()
            } else {
                showPermissionRationale()
            }
        }

        binding.btnSyncNow.setOnClickListener {
            viewModel.syncNow()
        }

        binding.btnConnectYandex.setOnClickListener {
            val intent = android.content.Intent(
                android.content.Intent.ACTION_VIEW,
                android.net.Uri.parse(com.example.roombooking.util.YandexConfig.getAuthUrl())
            )
            startActivity(intent)
        }

        binding.btnSaveFilterTags.setOnClickListener {
            viewModel.setFilterTags(binding.etFilterTags.text.toString())
            Toast.makeText(requireContext(), "Метки сохранены", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.yandexAuthorized.collect { authorized ->
                binding.tvYandexStatus.text = if (authorized) "Подключено ✓" else "Не подключено"
                binding.tvYandexStatus.setTextColor(
                    if (authorized)
                        androidx.core.content.ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark)
                    else
                        androidx.core.content.ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)
                )
                binding.btnConnectYandex.text =
                    if (authorized) "Переподключить" else "Подключить Яндекс Календарь"
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.syncEnabled.collect { enabled ->
                binding.switchSync.isChecked = enabled
                binding.groupSyncSettings.visibility =
                    if (enabled) View.VISIBLE else View.GONE
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.lastSyncTimeFormatted.collect { time ->
                binding.tvLastSync.text = "Последняя синхронизация: $time"
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.syncStatus.collect { status ->
                if (status.isNotBlank()) binding.tvSyncStatus.text = status
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.filterTags.collect { tags ->
                binding.etFilterTags.setText(tags)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.conflicts.collect { conflicts ->
                if (conflicts.isNotEmpty()) showConflictDialog(conflicts.first())
            }
        }
    }

    private fun showPermissionRationale() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Доступ к календарю")
            .setMessage("Приложению нужен доступ к вашему календарю для синхронизации мероприятий.")
            .setPositiveButton("Разрешить") { _, _ ->
                calendarPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR
                    )
                )
            }
            .setNegativeButton("Не сейчас", null)
            .show()
    }

    private fun showCalendarChooser() {
        val calendars = viewModel.availableCalendars.value
        if (calendars.isEmpty()) {
            Toast.makeText(requireContext(), "Нет доступных календарей", Toast.LENGTH_SHORT).show()
            return
        }
        val items = calendars.map { "${it.displayName} (${it.accountName})" }.toTypedArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Выберите календарь")
            .setItems(items) { _, which -> viewModel.selectCalendar(calendars[which].id) }
            .show()
    }

    private fun showConflictDialog(conflict: SyncConflictData) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Конфликт синхронизации")
            .setMessage(
                "«${conflict.appVersion.title}» изменено и в приложении, и в системном календаре. Какую версию сохранить?"
            )
            .setPositiveButton("Из приложения") { _, _ -> viewModel.resolveConflict(conflict) }
            .setNeutralButton("Из календаря") { _, _ -> viewModel.resolveConflict(conflict) }
            .setNegativeButton("Позже", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}