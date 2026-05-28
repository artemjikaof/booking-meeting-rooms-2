package com.example.roombooking.di;

import com.example.roombooking.data.local.AppDatabase;
import com.example.roombooking.data.local.EventDao;
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
public final class DatabaseModule_ProvideEventDaoFactory implements Factory<EventDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideEventDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public EventDao get() {
    return provideEventDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideEventDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideEventDaoFactory(dbProvider);
  }

  public static EventDao provideEventDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideEventDao(db));
  }
}
