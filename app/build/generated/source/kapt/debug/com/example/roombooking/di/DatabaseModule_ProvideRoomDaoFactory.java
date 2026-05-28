package com.example.roombooking.di;

import com.example.roombooking.data.local.AppDatabase;
import com.example.roombooking.data.local.RoomDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideRoomDaoFactory implements Factory<RoomDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideRoomDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public RoomDao get() {
    return provideRoomDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideRoomDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideRoomDaoFactory(dbProvider);
  }

  public static RoomDao provideRoomDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRoomDao(db));
  }
}
