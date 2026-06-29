package com.example.roombooking.data.repository;

import com.example.roombooking.data.local.EventDao;
import com.example.roombooking.data.local.RoomDao;
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

  private final Provider<RoomDao> roomDaoProvider;

  private final Provider<CalendarSyncManager> calendarSyncManagerProvider;

  private final Provider<SyncPreferences> prefsProvider;

  private final Provider<YandexCalendarRepository> yandexRepositoryProvider;

  public EventRepository_Factory(Provider<EventDao> eventDaoProvider,
      Provider<RoomDao> roomDaoProvider, Provider<CalendarSyncManager> calendarSyncManagerProvider,
      Provider<SyncPreferences> prefsProvider,
      Provider<YandexCalendarRepository> yandexRepositoryProvider) {
    this.eventDaoProvider = eventDaoProvider;
    this.roomDaoProvider = roomDaoProvider;
    this.calendarSyncManagerProvider = calendarSyncManagerProvider;
    this.prefsProvider = prefsProvider;
    this.yandexRepositoryProvider = yandexRepositoryProvider;
  }

  @Override
  public EventRepository get() {
    return newInstance(eventDaoProvider.get(), roomDaoProvider.get(), calendarSyncManagerProvider.get(), prefsProvider.get(), yandexRepositoryProvider.get());
  }

  public static EventRepository_Factory create(Provider<EventDao> eventDaoProvider,
      Provider<RoomDao> roomDaoProvider, Provider<CalendarSyncManager> calendarSyncManagerProvider,
      Provider<SyncPreferences> prefsProvider,
      Provider<YandexCalendarRepository> yandexRepositoryProvider) {
    return new EventRepository_Factory(eventDaoProvider, roomDaoProvider, calendarSyncManagerProvider, prefsProvider, yandexRepositoryProvider);
  }

  public static EventRepository newInstance(EventDao eventDao, RoomDao roomDao,
      CalendarSyncManager calendarSyncManager, SyncPreferences prefs,
      YandexCalendarRepository yandexRepository) {
    return new EventRepository(eventDao, roomDao, calendarSyncManager, prefs, yandexRepository);
  }
}
