package com.example.roombooking.util;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.example.roombooking.data.repository.EventRepository;
import com.example.roombooking.data.repository.SyncPreferences;
import dagger.internal.DaggerGenerated;
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
public final class CalendarSyncWorker_Factory {
  private final Provider<EventRepository> eventRepositoryProvider;

  private final Provider<SyncPreferences> syncPrefsProvider;

  public CalendarSyncWorker_Factory(Provider<EventRepository> eventRepositoryProvider,
      Provider<SyncPreferences> syncPrefsProvider) {
    this.eventRepositoryProvider = eventRepositoryProvider;
    this.syncPrefsProvider = syncPrefsProvider;
  }

  public CalendarSyncWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, eventRepositoryProvider.get(), syncPrefsProvider.get());
  }

  public static CalendarSyncWorker_Factory create(Provider<EventRepository> eventRepositoryProvider,
      Provider<SyncPreferences> syncPrefsProvider) {
    return new CalendarSyncWorker_Factory(eventRepositoryProvider, syncPrefsProvider);
  }

  public static CalendarSyncWorker newInstance(Context context, WorkerParameters params,
      EventRepository eventRepository, SyncPreferences syncPrefs) {
    return new CalendarSyncWorker(context, params, eventRepository, syncPrefs);
  }
}
