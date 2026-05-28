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
public final class EventDetailFragment_MembersInjector implements MembersInjector<EventDetailFragment> {
  private final Provider<EventRepository> eventRepositoryProvider;

  public EventDetailFragment_MembersInjector(Provider<EventRepository> eventRepositoryProvider) {
    this.eventRepositoryProvider = eventRepositoryProvider;
  }

  public static MembersInjector<EventDetailFragment> create(
      Provider<EventRepository> eventRepositoryProvider) {
    return new EventDetailFragment_MembersInjector(eventRepositoryProvider);
  }

  @Override
  public void injectMembers(EventDetailFragment instance) {
    injectEventRepository(instance, eventRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.example.roombooking.presentation.events.EventDetailFragment.eventRepository")
  public static void injectEventRepository(EventDetailFragment instance,
      EventRepository eventRepository) {
    instance.eventRepository = eventRepository;
  }
}
