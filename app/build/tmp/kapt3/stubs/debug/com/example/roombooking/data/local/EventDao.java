package com.example.roombooking.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000f\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ>\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\b\b\u0002\u0010\u0014\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0015J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\u0017H\'J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0019J$\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u001dJ\u0018\u0010\u001e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u001c\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\u00172\u0006\u0010\u0010\u001a\u00020\u0011H\'J\u001c\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\u00172\u0006\u0010\u000f\u001a\u00020\tH\'J\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\"\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\u00172\u0006\u0010$\u001a\u00020\u0011H\'J\u0016\u0010%\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006&"}, d2 = {"Lcom/example/roombooking/data/local/EventDao;", "", "deleteEvent", "", "event", "Lcom/example/roombooking/data/local/EventEntity;", "(Lcom/example/roombooking/data/local/EventEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteEventById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findByCalendarEventId", "calEventId", "findConflicts", "", "roomId", "date", "", "timeStart", "timeEnd", "excludeId", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllEvents", "Lkotlinx/coroutines/flow/Flow;", "getAllEventsSync", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDatesWithEvents", "from", "to", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEventById", "getEventsByDate", "getEventsByRoom", "getSyncableEvents", "insertEvent", "searchEvents", "query", "updateEvent", "app_debug"})
@androidx.room.Dao()
public abstract interface EventDao {
    
    @androidx.room.Query(value = "SELECT * FROM events ORDER BY dateStart ASC, timeStart ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.roombooking.data.local.EventEntity>> getAllEvents();
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE dateStart = :date ORDER BY timeStart ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.roombooking.data.local.EventEntity>> getEventsByDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE roomId = :roomId ORDER BY dateStart ASC, timeStart ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.roombooking.data.local.EventEntity>> getEventsByRoom(long roomId);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE title LIKE \'%\' || :query || \'%\' ORDER BY dateStart ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.roombooking.data.local.EventEntity>> searchEvents(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Query(value = "\n        SELECT * FROM events\n        WHERE roomId = :roomId\n          AND dateStart = :date\n          AND id != :excludeId\n          AND NOT (timeEnd <= :timeStart OR timeStart >= :timeEnd)\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object findConflicts(long roomId, @org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    java.lang.String timeStart, @org.jetbrains.annotations.NotNull()
    java.lang.String timeEnd, long excludeId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.roombooking.data.local.EventEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllEventsSync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.roombooking.data.local.EventEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE syncToDeviceCalendar = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSyncableEvents(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.example.roombooking.data.local.EventEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE deviceCalendarEventId = :calEventId LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object findByCalendarEventId(long calEventId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.roombooking.data.local.EventEntity> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertEvent(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.local.EventEntity event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateEvent(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.local.EventEntity event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteEvent(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.local.EventEntity event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM events WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteEventById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT dateStart FROM events WHERE dateStart BETWEEN :from AND :to")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDatesWithEvents(@org.jetbrains.annotations.NotNull()
    java.lang.String from, @org.jetbrains.annotations.NotNull()
    java.lang.String to, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM events WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEventById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.roombooking.data.local.EventEntity> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}