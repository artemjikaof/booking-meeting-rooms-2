package com.example.roombooking.presentation.settings;

import android.content.Context;
import com.example.roombooking.data.repository.CalendarSyncManager;
import com.example.roombooking.data.repository.EventRepository;
import com.example.roombooking.data.repository.SyncPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<SyncPreferences> syncPrefsProvider;

  private final Provider<CalendarSyncManager> calendarSyncManagerProvider;

  private final Provider<EventRepository> eventRepositoryProvider;

  public SettingsViewModel_Factory(Provider<Context> contextProvider,
      Provider<SyncPreferences> syncPrefsProvider,
      Provider<CalendarSyncManager> calendarSyncManagerProvider,
      Provider<EventRepository> eventRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.syncPrefsProvider = syncPrefsProvider;
    this.calendarSyncManagerProvider = calendarSyncManagerProvider;
    this.eventRepositoryProvider = eventRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(contextProvider.get(), syncPrefsProvider.get(), calendarSyncManagerProvider.get(), eventRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<Context> contextProvider,
      Provider<SyncPreferences> syncPrefsProvider,
      Provider<CalendarSyncManager> calendarSyncManagerProvider,
      Provider<EventRepository> eventRepositoryProvider) {
    return new SettingsViewModel_Factory(contextProvider, syncPrefsProvider, calendarSyncManagerProvider, eventRepositoryProvider);
  }

  public static SettingsViewModel newInstance(Context context, SyncPreferences syncPrefs,
      CalendarSyncManager calendarSyncManager, EventRepository eventRepository) {
    return new SettingsViewModel(context, syncPrefs, calendarSyncManager, eventRepository);
  }
}
