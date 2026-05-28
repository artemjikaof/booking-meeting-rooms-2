package com.example.roombooking.presentation.events;

import com.example.roombooking.data.repository.EventRepository;
import com.example.roombooking.data.repository.RoomRepository;
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

  public AddEditEventViewModel_Factory(Provider<EventRepository> eventRepositoryProvider,
      Provider<RoomRepository> roomRepositoryProvider) {
    this.eventRepositoryProvider = eventRepositoryProvider;
    this.roomRepositoryProvider = roomRepositoryProvider;
  }

  @Override
  public AddEditEventViewModel get() {
    return newInstance(eventRepositoryProvider.get(), roomRepositoryProvider.get());
  }

  public static AddEditEventViewModel_Factory create(
      Provider<EventRepository> eventRepositoryProvider,
      Provider<RoomRepository> roomRepositoryProvider) {
    return new AddEditEventViewModel_Factory(eventRepositoryProvider, roomRepositoryProvider);
  }

  public static AddEditEventViewModel newInstance(EventRepository eventRepository,
      RoomRepository roomRepository) {
    return new AddEditEventViewModel(eventRepository, roomRepository);
  }
}
