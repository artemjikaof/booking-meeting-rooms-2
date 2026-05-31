package com.example.roombooking.presentation.rooms;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0007J\u000e\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001b\u001a\u00020\u0016J\u001e\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\tJ\u000e\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\tR\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00110\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f\u00a8\u0006#"}, d2 = {"Lcom/example/roombooking/presentation/rooms/RoomsViewModel;", "Landroidx/lifecycle/ViewModel;", "roomRepository", "Lcom/example/roombooking/data/repository/RoomRepository;", "(Lcom/example/roombooking/data/repository/RoomRepository;)V", "_editingRoom", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/roombooking/domain/model/Room;", "_searchQuery", "", "_uiState", "Lcom/example/roombooking/presentation/events/UiState;", "editingRoom", "Lkotlinx/coroutines/flow/StateFlow;", "getEditingRoom", "()Lkotlinx/coroutines/flow/StateFlow;", "rooms", "", "getRooms", "uiState", "getUiState", "deleteRoom", "", "room", "loadRoom", "roomId", "", "resetState", "saveRoom", "name", "capacity", "", "description", "setSearchQuery", "query", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class RoomsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.RoomRepository roomRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.roombooking.domain.model.Room>> rooms = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.roombooking.presentation.events.UiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.presentation.events.UiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.roombooking.domain.model.Room> _editingRoom = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.domain.model.Room> editingRoom = null;
    
    @javax.inject.Inject()
    public RoomsViewModel(@org.jetbrains.annotations.NotNull()
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
    public final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.domain.model.Room> getEditingRoom() {
        return null;
    }
    
    public final void setSearchQuery(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void loadRoom(long roomId) {
    }
    
    public final void saveRoom(@org.jetbrains.annotations.NotNull()
    java.lang.String name, int capacity, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
    }
    
    public final void deleteRoom(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.domain.model.Room room) {
    }
    
    public final void resetState() {
    }
}