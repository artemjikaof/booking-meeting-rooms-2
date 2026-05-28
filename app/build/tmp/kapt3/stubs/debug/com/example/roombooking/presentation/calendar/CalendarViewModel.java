package com.example.roombooking.presentation.calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u001e\u001a\u00020\u001fH\u0002J\u0006\u0010 \u001a\u00020\u001fJ\u0006\u0010!\u001a\u00020\u001fJ\u000e\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020\rJ\u000e\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u000fR\u001c\u0010\u0005\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00070\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\r0\r0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00170\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013\u00a8\u0006&"}, d2 = {"Lcom/example/roombooking/presentation/calendar/CalendarViewModel;", "Landroidx/lifecycle/ViewModel;", "eventRepository", "Lcom/example/roombooking/data/repository/EventRepository;", "(Lcom/example/roombooking/data/repository/EventRepository;)V", "_currentMonth", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Ljava/time/YearMonth;", "kotlin.jvm.PlatformType", "_datesWithEvents", "", "", "_selectedDate", "Ljava/time/LocalDate;", "_viewMode", "Lcom/example/roombooking/presentation/calendar/CalendarViewMode;", "currentMonth", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentMonth", "()Lkotlinx/coroutines/flow/StateFlow;", "datesWithEvents", "getDatesWithEvents", "eventsForSelectedDate", "", "Lcom/example/roombooking/domain/model/Event;", "getEventsForSelectedDate", "selectedDate", "getSelectedDate", "viewMode", "getViewMode", "loadDatesWithEvents", "", "navigateToNextMonth", "navigateToPreviousMonth", "selectDate", "date", "setViewMode", "mode", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CalendarViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.EventRepository eventRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.time.LocalDate> _selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.time.LocalDate> selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.time.YearMonth> _currentMonth = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.time.YearMonth> currentMonth = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.roombooking.domain.model.Event>> eventsForSelectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.Set<java.lang.String>> _datesWithEvents = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> datesWithEvents = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.roombooking.presentation.calendar.CalendarViewMode> _viewMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.presentation.calendar.CalendarViewMode> viewMode = null;
    
    @javax.inject.Inject()
    public CalendarViewModel(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.EventRepository eventRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.time.LocalDate> getSelectedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.time.YearMonth> getCurrentMonth() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.roombooking.domain.model.Event>> getEventsForSelectedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.Set<java.lang.String>> getDatesWithEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.roombooking.presentation.calendar.CalendarViewMode> getViewMode() {
        return null;
    }
    
    public final void selectDate(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date) {
    }
    
    public final void navigateToPreviousMonth() {
    }
    
    public final void navigateToNextMonth() {
    }
    
    public final void setViewMode(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.presentation.calendar.CalendarViewMode mode) {
    }
    
    private final void loadDatesWithEvents() {
    }
}