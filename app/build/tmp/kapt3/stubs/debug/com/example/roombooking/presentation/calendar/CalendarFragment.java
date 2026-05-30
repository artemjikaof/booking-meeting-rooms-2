package com.example.roombooking.presentation.calendar;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J$\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0017H\u0016J\u001a\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u00192\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u0010\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020\u0017H\u0002J\u0016\u0010\'\u001a\u00020\u00172\f\u0010(\u001a\b\u0012\u0004\u0012\u00020%0)H\u0002J\b\u0010*\u001a\u00020\u0017H\u0002J\b\u0010+\u001a\u00020\u0017H\u0002J\b\u0010,\u001a\u00020\u0017H\u0002J\b\u0010-\u001a\u00020\u0017H\u0002J\b\u0010.\u001a\u00020\u0017H\u0002J\b\u0010/\u001a\u00020\u0017H\u0002J\b\u00100\u001a\u00020\u0017H\u0002J\b\u00101\u001a\u00020\u0017H\u0002J\b\u00102\u001a\u00020\u0017H\u0002J&\u00103\u001a\u00020\u00172\b\b\u0002\u00104\u001a\u0002052\b\b\u0002\u00106\u001a\u0002072\b\b\u0002\u00108\u001a\u000209H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006:"}, d2 = {"Lcom/example/roombooking/presentation/calendar/CalendarFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/example/roombooking/databinding/FragmentCalendarBinding;", "binding", "getBinding", "()Lcom/example/roombooking/databinding/FragmentCalendarBinding;", "calendarAdapter", "Lcom/example/roombooking/presentation/calendar/CalendarGridAdapter;", "eventsAdapter", "Lcom/example/roombooking/presentation/calendar/EventsAdapter;", "handler", "Landroid/os/Handler;", "updateTimeRunnable", "Ljava/lang/Runnable;", "viewModel", "Lcom/example/roombooking/presentation/calendar/CalendarViewModel;", "getViewModel", "()Lcom/example/roombooking/presentation/calendar/CalendarViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "observeViewModel", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "openEventDetail", "event", "Lcom/example/roombooking/domain/model/Event;", "refreshCalendar", "renderEventsOnSchedule", "events", "", "scrollToCurrentTime", "setupCalendarGrid", "setupDayView", "setupMenu", "setupNavigation", "setupRecyclerView", "setupWeekdaysRow", "updateDayTitle", "updateRedLine", "updateTodayButtonVisibility", "selectedDate", "Ljava/time/LocalDate;", "currentMonth", "Ljava/time/YearMonth;", "viewMode", "Lcom/example/roombooking/presentation/calendar/CalendarViewMode;", "app_debug"})
public final class CalendarFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.example.roombooking.databinding.FragmentCalendarBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.example.roombooking.presentation.calendar.EventsAdapter eventsAdapter;
    private com.example.roombooking.presentation.calendar.CalendarGridAdapter calendarAdapter;
    @org.jetbrains.annotations.NotNull()
    private final android.os.Handler handler = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.Runnable updateTimeRunnable = null;
    
    public CalendarFragment() {
        super();
    }
    
    private final com.example.roombooking.databinding.FragmentCalendarBinding getBinding() {
        return null;
    }
    
    private final com.example.roombooking.presentation.calendar.CalendarViewModel getViewModel() {
        return null;
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
    
    private final void setupMenu() {
    }
    
    private final void setupWeekdaysRow() {
    }
    
    private final void setupCalendarGrid() {
    }
    
    private final void setupDayView() {
    }
    
    private final void updateRedLine() {
    }
    
    private final void renderEventsOnSchedule(java.util.List<com.example.roombooking.domain.model.Event> events) {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupNavigation() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void refreshCalendar() {
    }
    
    private final void updateDayTitle() {
    }
    
    private final void scrollToCurrentTime() {
    }
    
    private final void updateTodayButtonVisibility(java.time.LocalDate selectedDate, java.time.YearMonth currentMonth, com.example.roombooking.presentation.calendar.CalendarViewMode viewMode) {
    }
    
    private final void openEventDetail(com.example.roombooking.domain.model.Event event) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}