package com.example.roombooking.presentation.events;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\'\u001a\u00020(2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0002J\b\u0010*\u001a\u00020(H\u0002J$\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u0001002\b\u00101\u001a\u0004\u0018\u000102H\u0016J\b\u00103\u001a\u00020(H\u0016J\u001a\u00104\u001a\u00020(2\u0006\u00105\u001a\u00020,2\b\u00101\u001a\u0004\u0018\u000102H\u0016J\u0010\u00106\u001a\u00020(2\u0006\u00107\u001a\u00020\u0012H\u0002J\u0010\u00108\u001a\u00020(2\u0006\u00107\u001a\u00020\u0012H\u0002J\b\u00109\u001a\u00020(H\u0002J\b\u0010:\u001a\u00020(H\u0002J\u0018\u0010;\u001a\u00020(2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020=H\u0002J\b\u0010?\u001a\u00020(H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\b\u001a\n \n*\u0004\u0018\u00010\t0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00128BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0015\u001a\n \n*\u0004\u0018\u00010\t0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0016\u001a\n \n*\u0004\u0018\u00010\t0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\n \n*\u0004\u0018\u00010\u001b0\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001c\u001a\n \n*\u0004\u0018\u00010\u001b0\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u001e\u001a\n \n*\u0004\u0018\u00010\u001f0\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010 \u001a\n \n*\u0004\u0018\u00010\u001f0\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010!\u001a\n \n*\u0004\u0018\u00010\t0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\"\u001a\u00020#8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b&\u0010\u0010\u001a\u0004\b$\u0010%\u00a8\u0006@"}, d2 = {"Lcom/example/roombooking/presentation/events/AddEditEventFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/example/roombooking/databinding/FragmentAddEditEventBinding;", "binding", "getBinding", "()Lcom/example/roombooking/databinding/FragmentAddEditEventBinding;", "dateFmt", "Ljava/time/format/DateTimeFormatter;", "kotlin.jvm.PlatformType", "eventId", "", "getEventId", "()J", "eventId$delegate", "Lkotlin/Lazy;", "formFilledFromEvent", "", "isEditing", "()Z", "isoDate", "isoTime", "roomsList", "", "Lcom/example/roombooking/domain/model/Room;", "selectedDateEnd", "Ljava/time/LocalDate;", "selectedDateStart", "selectedRoom", "selectedTimeEnd", "Ljava/time/LocalTime;", "selectedTimeStart", "timeFmt", "viewModel", "Lcom/example/roombooking/presentation/events/AddEditEventViewModel;", "getViewModel", "()Lcom/example/roombooking/presentation/events/AddEditEventViewModel;", "viewModel$delegate", "fillFormFromEvent", "", "rooms", "observeViewModel", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "pickDate", "isStart", "pickTime", "save", "setupClicks", "showConflict", "room", "", "time", "updateDisplays", "app_debug"})
public final class AddEditEventFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.example.roombooking.databinding.FragmentAddEditEventBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy eventId$delegate = null;
    private java.time.LocalDate selectedDateStart;
    private java.time.LocalDate selectedDateEnd;
    private java.time.LocalTime selectedTimeStart;
    private java.time.LocalTime selectedTimeEnd;
    @org.jetbrains.annotations.Nullable()
    private com.example.roombooking.domain.model.Room selectedRoom;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.example.roombooking.domain.model.Room> roomsList;
    private boolean formFilledFromEvent = false;
    private final java.time.format.DateTimeFormatter dateFmt = null;
    private final java.time.format.DateTimeFormatter timeFmt = null;
    private final java.time.format.DateTimeFormatter isoDate = null;
    private final java.time.format.DateTimeFormatter isoTime = null;
    
    public AddEditEventFragment() {
        super();
    }
    
    private final com.example.roombooking.databinding.FragmentAddEditEventBinding getBinding() {
        return null;
    }
    
    private final com.example.roombooking.presentation.events.AddEditEventViewModel getViewModel() {
        return null;
    }
    
    private final long getEventId() {
        return 0L;
    }
    
    private final boolean isEditing() {
        return false;
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
    
    private final void setupClicks() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void fillFormFromEvent(java.util.List<com.example.roombooking.domain.model.Room> rooms) {
    }
    
    private final void save() {
    }
    
    private final void pickDate(boolean isStart) {
    }
    
    private final void pickTime(boolean isStart) {
    }
    
    private final void updateDisplays() {
    }
    
    private final void showConflict(java.lang.String room, java.lang.String time) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}