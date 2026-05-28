package com.example.roombooking.presentation.events;

import com.example.roombooking.data.repository.EventRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class EventsListFragment_MembersInjector implements MembersInjector<EventsListFragment> {
  private final Provider<EventRepository> eventRepositoryProvider;

  public EventsListFragment_MembersInjector(Provider<EventRepository> eventRepositoryProvider) {
    this.eventRepositoryProvider = eventRepositoryProvider;
  }

  public static MembersInjector<EventsListFragment> create(
      Provider<EventRepository> eventRepositoryProvider) {
    return new EventsListFragment_MembersInjector(eventRepositoryProvider);
  }

  @Override
  public void injectMembers(EventsListFragment instance) {
    injectEventRepository(instance, eventRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.example.roombooking.presentation.events.EventsListFragment.eventRepository")
  public static void injectEventRepository(EventsListFragment instance,
      EventRepository eventRepository) {
    instance.eventRepository = eventRepository;
  }
}
