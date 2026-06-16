package com.example.roombooking.presentation.events;

import com.example.roombooking.data.repository.EventRepository;
import com.example.roombooking.data.repository.RoomRepository;
import com.example.roombooking.data.repository.YandexCalendarRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class AddEditEventViewModel_Factory implements Factory<AddEditEventViewModel> {
  private final Provider<EventRepository> eventRepositoryProvider;

  private final Provider<RoomRepository> roomRepositoryProvider;

  private final Provider<YandexCalendarRepository> yandexRepositoryProvider;

  public AddEditEventViewModel_Factory(Provider<EventRepository> eventRepositoryProvider,
      Provider<RoomRepository> roomRepositoryProvider,
      Provider<YandexCalendarRepository> yandexRepositoryProvider) {
    this.eventRepositoryProvider = eventRepositoryProvider;
    this.roomRepositoryProvider = roomRepositoryProvider;
    this.yandexRepositoryProvider = yandexRepositoryProvider;
  }

  @Override
  public AddEditEventViewModel get() {
    return newInstance(eventRepositoryProvider.get(), roomRepositoryProvider.get(), yandexRepositoryProvider.get());
  }

  public static AddEditEventViewModel_Factory create(
      Provider<EventRepository> eventRepositoryProvider,
      Provider<RoomRepository> roomRepositoryProvider,
      Provider<YandexCalendarRepository> yandexRepositoryProvider) {
    return new AddEditEventViewModel_Factory(eventRepositoryProvider, roomRepositoryProvider, yandexRepositoryProvider);
  }

  public static AddEditEventViewModel newInstance(EventRepository eventRepository,
      RoomRepository roomRepository, YandexCalendarRepository yandexRepository) {
    return new AddEditEventViewModel(eventRepository, roomRepository, yandexRepository);
  }
}
