package com.example.roombooking.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\u001d\u0010\u0010\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\f\u00a2\u0006\u0002\u0010\u0014J\"\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000e2\u0006\u0010\u0013\u001a\u00020\f2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u000eJ\u0018\u0010\u0019\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u001b\u001a\u00020\u0018H\u0002J\u0016\u0010\u001c\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u001d"}, d2 = {"Lcom/example/roombooking/data/repository/CalendarSyncManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "cr", "Landroid/content/ContentResolver;", "getCr", "()Landroid/content/ContentResolver;", "deleteEventFromCalendar", "", "calendarEventId", "", "getAvailableCalendars", "", "Lcom/example/roombooking/data/repository/DeviceCalendar;", "insertEventToCalendar", "event", "Lcom/example/roombooking/domain/model/Event;", "calendarId", "(Lcom/example/roombooking/domain/model/Event;J)Ljava/lang/Long;", "readCalendarEvents", "Lcom/example/roombooking/data/repository/CalendarEventData;", "filterTags", "", "toMillis", "dateStr", "timeStr", "updateEventInCalendar", "app_debug"})
public final class CalendarSyncManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public CalendarSyncManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.content.ContentResolver getCr() {
        return null;
    }
    
    private final long toMillis(java.lang.String dateStr, java.lang.String timeStr) {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long insertEventToCalendar(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.domain.model.Event event, long calendarId) {
        return null;
    }
    
    public final void updateEventInCalendar(long calendarEventId, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.domain.model.Event event) {
    }
    
    public final void deleteEventFromCalendar(long calendarEventId) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.roombooking.data.repository.CalendarEventData> readCalendarEvents(long calendarId, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> filterTags) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.roombooking.data.repository.DeviceCalendar> getAvailableCalendars() {
        return null;
    }
}