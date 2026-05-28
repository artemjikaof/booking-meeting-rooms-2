package com.example.roombooking.data.repository;

import com.example.roombooking.data.local.EventDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class EventRepository_Factory implements Factory<EventRepository> {
  private final Provider<EventDao> eventDaoProvider;

  private final Provider<CalendarSyncManager> calendarSyncManagerProvider;

  private final Provider<SyncPreferences> prefsProvider;

  public EventRepository_Factory(Provider<EventDao> eventDaoProvider,
      Provider<CalendarSyncManager> calendarSyncManagerProvider,
      Provider<SyncPreferences> prefsProvider) {
    this.eventDaoProvider = eventDaoProvider;
    this.calendarSyncManagerProvider = calendarSyncManagerProvider;
    this.prefsProvider = prefsProvider;
  }

  @Override
  public EventRepository get() {
    return newInstance(eventDaoProvider.get(), calendarSyncManagerProvider.get(), prefsProvider.get());
  }

  public static EventRepository_Factory create(Provider<EventDao> eventDaoProvider,
      Provider<CalendarSyncManager> calendarSyncManagerProvider,
      Provider<SyncPreferences> prefsProvider) {
    return new EventRepository_Factory(eventDaoProvider, calendarSyncManagerProvider, prefsProvider);
  }

  public static EventRepository newInstance(EventDao eventDao,
      CalendarSyncManager calendarSyncManager, SyncPreferences prefs) {
    return new EventRepository(eventDao, calendarSyncManager, prefs);
  }
}
