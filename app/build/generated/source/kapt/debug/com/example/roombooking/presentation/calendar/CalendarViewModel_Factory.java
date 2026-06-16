package com.example.roombooking.presentation.calendar;

import com.example.roombooking.data.repository.EventRepository;
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
public final class CalendarViewModel_Factory implements Factory<CalendarViewModel> {
  private final Provider<EventRepository> eventRepositoryProvider;

  private final Provider<YandexCalendarRepository> yandexRepositoryProvider;

  public CalendarViewModel_Factory(Provider<EventRepository> eventRepositoryProvider,
      Provider<YandexCalendarRepository> yandexRepositoryProvider) {
    this.eventRepositoryProvider = eventRepositoryProvider;
    this.yandexRepositoryProvider = yandexRepositoryProvider;
  }

  @Override
  public CalendarViewModel get() {
    return newInstance(eventRepositoryProvider.get(), yandexRepositoryProvider.get());
  }

  public static CalendarViewModel_Factory create(Provider<EventRepository> eventRepositoryProvider,
      Provider<YandexCalendarRepository> yandexRepositoryProvider) {
    return new CalendarViewModel_Factory(eventRepositoryProvider, yandexRepositoryProvider);
  }

  public static CalendarViewModel newInstance(EventRepository eventRepository,
      YandexCalendarRepository yandexRepository) {
    return new CalendarViewModel(eventRepository, yandexRepository);
  }
}
