package com.example.roombooking.presentation.events;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eJN\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020!2\u0006\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020!2\u0006\u0010&\u001a\u00020\u00162\u0006\u0010\'\u001a\u00020!2\u0006\u0010(\u001a\u00020!2\u0006\u0010)\u001a\u00020\u0013J\u000e\u0010*\u001a\u00020\u001b2\u0006\u0010+\u001a\u00020\u0013R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/example/roombooking/presentation/events/AddEditEventViewModel;", "Landroidx/lifecycle/ViewModel;", "eventRepository", "Lcom/example/roombooking/data/repository/EventRepository;", "roomRepository", "Lcom/example/roombooking/data/repository/RoomRepository;", "yandexRepository", "Lcom/example/roombooking/data/repository/YandexCalendarRepository;", "(Lcom/example/roombooking/data/repository/EventRepository;Lcom/example/roombooking/data/repository/RoomRepository;Lcom/example/roombooking/data/repository/YandexCalendarRepository;)V", "_currentEvent", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/roombooking/domain/model/Event;", "_uiState", "Lcom/example/roombooking/presentation/events/UiState;", "currentEvent", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentEvent", "()Lkotlinx/coroutines/flow/StateFlow;", "forceSave", "", "rooms", "", "Lcom/example/roombooking/domain/model/Room;", "getRooms", "uiState", "getUiState", "deleteEvent", "", "loadEvent", "eventId", "", "saveEvent", "title", "", "dateStart", "dateEnd", "timeStart", "timeEnd", "room", "description", "participants", "syncToCalendar", "setForceSave", "value", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AddEditEventViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.EventRepository eventRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.RoomRepository roomRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.YandexCalendarRepository yandexRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.roombooking.domain.model.Room>> rooms = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.roombooking.presentation.events.UiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.presentation.events.UiState> uiState = null;
    private boolean forceSave = false;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.roombooking.domain.model.Event> _currentEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.domain.model.Event> currentEvent = null;
    
    @javax.inject.Inject()
    public AddEditEventViewModel(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.EventRepository eventRepository, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.RoomRepository roomRepository, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.YandexCalendarRepository yandexRepository) {
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
    
    public final void setForceSave(boolean value) {
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