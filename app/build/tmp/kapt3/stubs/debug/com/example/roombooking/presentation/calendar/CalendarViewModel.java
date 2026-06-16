package com.example.roombooking.presentation.calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001a\u0010!\u001a\u0004\u0018\u00010\u00142\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\b\u0010&\u001a\u00020\'H\u0002J\b\u0010(\u001a\u00020\'H\u0002J\u0006\u0010)\u001a\u00020\'J\u0006\u0010*\u001a\u00020\'J\u000e\u0010+\u001a\u00020\'2\u0006\u0010,\u001a\u00020\u000fJ\u0006\u0010-\u001a\u00020\'J\u000e\u0010.\u001a\u00020\'2\u0006\u0010/\u001a\u00020\u0011R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000e\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\u000f0\u000f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00130\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2 = {"Lcom/example/roombooking/presentation/calendar/CalendarViewModel;", "Landroidx/lifecycle/ViewModel;", "eventRepository", "Lcom/example/roombooking/data/repository/EventRepository;", "yandexRepository", "Lcom/example/roombooking/data/repository/YandexCalendarRepository;", "(Lcom/example/roombooking/data/repository/EventRepository;Lcom/example/roombooking/data/repository/YandexCalendarRepository;)V", "_currentMonth", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Ljava/time/YearMonth;", "kotlin.jvm.PlatformType", "_datesWithEvents", "", "", "_selectedDate", "Ljava/time/LocalDate;", "_viewMode", "Lcom/example/roombooking/presentation/calendar/CalendarViewMode;", "_yandexEvents", "", "Lcom/example/roombooking/domain/model/Event;", "currentMonth", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentMonth", "()Lkotlinx/coroutines/flow/StateFlow;", "datesWithEvents", "getDatesWithEvents", "eventsForSelectedDate", "getEventsForSelectedDate", "selectedDate", "getSelectedDate", "viewMode", "getViewMode", "calendarEventDataToEvent", "data", "Lcom/example/roombooking/data/repository/CalendarEventData;", "zone", "Ljava/time/ZoneId;", "loadDatesWithEvents", "", "loadYandexEvents", "navigateToNextMonth", "navigateToPreviousMonth", "selectDate", "date", "selectToday", "setViewMode", "mode", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CalendarViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.EventRepository eventRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.YandexCalendarRepository yandexRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.time.LocalDate> _selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.time.LocalDate> selectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.roombooking.domain.model.Event>> _yandexEvents = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.roombooking.domain.model.Event>> eventsForSelectedDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.time.YearMonth> _currentMonth = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.time.YearMonth> currentMonth = null;
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
    com.example.roombooking.data.repository.EventRepository eventRepository, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.YandexCalendarRepository yandexRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.time.LocalDate> getSelectedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.roombooking.domain.model.Event>> getEventsForSelectedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.time.YearMonth> getCurrentMonth() {
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
    
    private final void loadYandexEvents() {
    }
    
    private final com.example.roombooking.domain.model.Event calendarEventDataToEvent(com.example.roombooking.data.repository.CalendarEventData data, java.time.ZoneId zone) {
        return null;
    }
    
    public final void selectToday() {
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