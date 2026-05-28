package com.example.roombooking.presentation.rooms;

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
public final class RoomsViewModel_Factory implements Factory<RoomsViewModel> {
  private final Provider<RoomRepository> roomRepositoryProvider;

  public RoomsViewModel_Factory(Provider<RoomRepository> roomRepositoryProvider) {
    this.roomRepositoryProvider = roomRepositoryProvider;
  }

  @Override
  public RoomsViewModel get() {
    return newInstance(roomRepositoryProvider.get());
  }

  public static RoomsViewModel_Factory create(Provider<RoomRepository> roomRepositoryProvider) {
    return new RoomsViewModel_Factory(roomRepositoryProvider);
  }

  public static RoomsViewModel newInstance(RoomRepository roomRepository) {
    return new RoomsViewModel(roomRepository);
  }
}
