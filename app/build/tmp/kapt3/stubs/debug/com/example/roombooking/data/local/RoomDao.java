package com.example.roombooking.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000e0\rH\'J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0010\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0011\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000e0\r2\u0006\u0010\u0013\u001a\u00020\u0014H\'J\u0016\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/example/roombooking/data/local/RoomDao;", "", "countEventsByRoom", "", "roomId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRoom", "", "room", "Lcom/example/roombooking/data/local/RoomEntity;", "(Lcom/example/roombooking/data/local/RoomEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllRooms", "Lkotlinx/coroutines/flow/Flow;", "", "getRoomById", "id", "insertRoom", "searchRooms", "query", "", "updateRoom", "app_debug"})
@androidx.room.Dao()
public abstract interface RoomDao {
    
    @androidx.room.Query(value = "SELECT * FROM rooms ORDER BY name ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.roombooking.data.local.RoomEntity>> getAllRooms();
    
    @androidx.room.Query(value = "SELECT * FROM rooms WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRoomById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.roombooking.data.local.RoomEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM rooms WHERE name LIKE \'%\' || :query || \'%\'")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.roombooking.data.local.RoomEntity>> searchRooms(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertRoom(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.local.RoomEntity room, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateRoom(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.local.RoomEntity room, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteRoom(@org.jetbrains.annotations.NotNull()
    com.example.roombooking.data.local.RoomEntity room, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM events WHERE roomId = :roomId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object countEventsByRoom(long roomId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}