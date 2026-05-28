package com.example.roombooking.presentation.events;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aJN\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020\u00122\u0006\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u001d2\u0006\u0010%\u001a\u00020&R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000f\u00a8\u0006\'"}, d2 = {"Lcom/example/roombooking/presentation/events/AddEditEventViewModel;", "Landroidx/lifecycle/ViewModel;", "eventRepository", "Lcom/example/roombooking/data/repository/EventRepository;", "roomRepository", "Lcom/example/roombooking/data/repository/RoomRepository;", "(Lcom/example/roombooking/data/repository/EventRepository;Lcom/example/roombooking/data/repository/RoomRepository;)V", "_currentEvent", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/roombooking/domain/model/Event;", "_uiState", "Lcom/example/roombooking/presentation/events/UiState;", "currentEvent", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentEvent", "()Lkotlinx/coroutines/flow/StateFlow;", "rooms", "", "Lcom/example/roombooking/domain/model/Room;", "getRooms", "uiState", "getUiState", "deleteEvent", "", "loadEvent", "eventId", "", "saveEvent", "title", "", "dateStart", "dateEnd", "timeStart", "timeEnd", "room", "description", "participants", "syncToCalendar", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AddEditEventViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.EventRepository eventRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.RoomRepository roomRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.roombooking.domain.model.Room>> rooms = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.roombooking.presentation.events.UiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.presentation.events.UiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.roombooking.domain.model.Event> _currentEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.domain.model.Event> currentEvent = null;
    
    @javax.inject.Inject()
    public AddEditEventViewModel(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.EventRepository eventRepository, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.RoomRepository roomRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.roombooking.domain.model.Room>> getRooms() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.presentation.events.UiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.domain.model.Event> getCurrentEvent() {
        return null;
    }
    
    public final void loadEvent(long eventId) {
    }
    
    public final void saveEvent(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String dateStart, @org.jetbrains.annotations.NotNull()
    java.lang.String dateEnd, @org.jetbrains.annotations.NotNull()
    java.lang.String timeStart, @org.jetbrains.annotations.NotNull()
    java.lang.String timeEnd, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.domain.model.Room room, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.lang.String participants, boolean syncToCalendar) {
    }
    
    public final void deleteEvent() {
    }
}