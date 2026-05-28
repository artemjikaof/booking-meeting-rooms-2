package com.example.roombooking.data.repository;

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
public final class RoomRepository_Factory implements Factory<RoomRepository> {
  private final Provider<RoomDao> roomDaoProvider;

  public RoomRepository_Factory(Provider<RoomDao> roomDaoProvider) {
    this.roomDaoProvider = roomDaoProvider;
  }

  @Override
  public RoomRepository get() {
    return newInstance(roomDaoProvider.get());
  }

  public static RoomRepository_Factory create(Provider<RoomDao> roomDaoProvider) {
    return new RoomRepository_Factory(roomDaoProvider);
  }

  public static RoomRepository newInstance(RoomDao roomDao) {
    return new RoomRepository(roomDao);
  }
}
