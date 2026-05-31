package com.example.roombooking.presentation.events;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\tH\u0002J\b\u0010\u0019\u001a\u00020\u0017H\u0002J\b\u0010\u001a\u001a\u00020\u0017H\u0002J$\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010#\u001a\u00020\u0017H\u0016J\u001a\u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020\u001c2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u001e\u0010\u0010\u001a\u00020\u00118\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006&"}, d2 = {"Lcom/example/roombooking/presentation/events/EventDetailFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/example/roombooking/databinding/FragmentEventDetailBinding;", "binding", "getBinding", "()Lcom/example/roombooking/databinding/FragmentEventDetailBinding;", "currentEvent", "Lcom/example/roombooking/domain/model/Event;", "eventId", "", "getEventId", "()J", "eventId$delegate", "Lkotlin/Lazy;", "eventRepository", "Lcom/example/roombooking/data/repository/EventRepository;", "getEventRepository", "()Lcom/example/roombooking/data/repository/EventRepository;", "setEventRepository", "(Lcom/example/roombooking/data/repository/EventRepository;)V", "bindEvent", "", "event", "confirmDelete", "loadEvent", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "app_debug"})
public final class EventDetailFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.example.roombooking.databinding.FragmentEventDetailBinding _binding;
    @javax.inject.Inject()
    public com.example.roombooking.data.repository.EventRepository eventRepository;
    @org.jetbrains.annotations.Nullable()
    private com.example.roombooking.domain.model.Event currentEvent;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy eventId$delegate = null;
    
    public EventDetailFragment() {
        super();
    }
    
    private final com.example.roombooking.databinding.FragmentEventDetailBinding getBinding() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.roombooking.data.repository.EventRepository getEventRepository() {
        return null;
    }
    
    public final void setEventRepository(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.EventRepository p0) {
    }
    
    private final long getEventId() {
        return 0L;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadEvent() {
    }
    
    private final void bindEvent(com.example.roombooking.domain.model.Event event) {
    }
    
    private final void confirmDelete() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}