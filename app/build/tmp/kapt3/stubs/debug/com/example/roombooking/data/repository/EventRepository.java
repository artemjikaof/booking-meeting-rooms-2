package com.example.roombooking.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00120\u0011J$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00122\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u001a\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00120\u00112\u0006\u0010\u001d\u001a\u00020\u0014J\u001a\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00120\u00112\u0006\u0010\u001f\u001a\u00020\u001aJ8\u0010 \u001a\u00020!2\u0006\u0010\u001f\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\u00142\b\b\u0002\u0010$\u001a\u00020\u001aH\u0086@\u00a2\u0006\u0002\u0010%J\u0016\u0010&\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u001a\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u00120\u00112\u0006\u0010(\u001a\u00020\u0014J\u0014\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u0012H\u0086@\u00a2\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/example/roombooking/data/repository/EventRepository;", "", "eventDao", "Lcom/example/roombooking/data/local/EventDao;", "calendarSyncManager", "Lcom/example/roombooking/data/repository/CalendarSyncManager;", "prefs", "Lcom/example/roombooking/data/repository/SyncPreferences;", "yandexRepository", "Lcom/example/roombooking/data/repository/YandexCalendarRepository;", "(Lcom/example/roombooking/data/local/EventDao;Lcom/example/roombooking/data/repository/CalendarSyncManager;Lcom/example/roombooking/data/repository/SyncPreferences;Lcom/example/roombooking/data/repository/YandexCalendarRepository;)V", "deleteEvent", "", "event", "Lcom/example/roombooking/domain/model/Event;", "(Lcom/example/roombooking/domain/model/Event;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllEvents", "Lkotlinx/coroutines/flow/Flow;", "", "getDatesWithEvents", "", "from", "to", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEventById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEventsByDate", "date", "getEventsByRoom", "roomId", "hasConflict", "", "timeStart", "timeEnd", "excludeId", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertEvent", "searchEvents", "query", "syncWithDeviceCalendar", "Lcom/example/roombooking/data/repository/SyncConflictData;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateEvent", "app_debug"})
public final class EventRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.local.EventDao eventDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.CalendarSyncManager calendarSyncManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.SyncPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.roombooking.data.repository.YandexCalendarRepository yandexRepository = null;
    
    @javax.inject.Inject()
    public EventRepository(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.local.EventDao eventDao, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.CalendarSyncManager calendarSyncManager, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.SyncPreferences prefs, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.repository.YandexCalendarRepository yandexRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.roombooking.domain.model.Event>> getAllEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.roombooking.domain.model.Event>> getEventsByDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.roombooking.domain.model.Event>> getEventsByRoom(long roomId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.example.roombooking.domain.model.Event>> searchEvents(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getEventById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.roombooking.domain.model.Event> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getDatesWithEvents(@org.jetbrains.annotations.NotNull()
    java.lang.String from, @org.jetbrains.annotations.NotNull()
    java.lang.String to, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object hasConflict(long roomId, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String timeStart, @org.jetbrains.annotations.NotNull()
    java.lang.String timeEnd, long excludeId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object insertEvent(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.domain.model.Event event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateEvent(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.domain.model.Event event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteEvent(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.domain.model.Event event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncWithDeviceCalendar(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.roombooking.data.repository.SyncConflictData>> $completion) {
        return null;
    }
}