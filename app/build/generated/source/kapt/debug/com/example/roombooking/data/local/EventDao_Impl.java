package com.example.roombooking.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EventDao_Impl implements EventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EventEntity> __insertionAdapterOfEventEntity;

  private final EntityDeletionOrUpdateAdapter<EventEntity> __deletionAdapterOfEventEntity;

  private final EntityDeletionOrUpdateAdapter<EventEntity> __updateAdapterOfEventEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteEventById;

  public EventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEventEntity = new EntityInsertionAdapter<EventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `events` (`id`,`title`,`dateStart`,`dateEnd`,`timeStart`,`timeEnd`,`roomId`,`roomName`,`description`,`participants`,`syncToDeviceCalendar`,`deviceCalendarEventId`,`yandexEventId`,`fromDeviceCalendar`,`lastModifiedInApp`,`lastModifiedInCalendar`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EventEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDateStart() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDateStart());
        }
        if (entity.getDateEnd() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDateEnd());
        }
        if (entity.getTimeStart() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTimeStart());
        }
        if (entity.getTimeEnd() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTimeEnd());
        }
        statement.bindLong(7, entity.getRoomId());
        if (entity.getRoomName() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getRoomName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getDescription());
        }
        if (entity.getParticipants() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getParticipants());
        }
        final int _tmp = entity.getSyncToDeviceCalendar() ? 1 : 0;
        statement.bindLong(11, _tmp);
        if (entity.getDeviceCalendarEventId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getDeviceCalendarEventId());
        }
        if (entity.getYandexEventId() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getYandexEventId());
        }
        final int _tmp_1 = entity.getFromDeviceCalendar() ? 1 : 0;
        statement.bindLong(14, _tmp_1);
        statement.bindLong(15, entity.getLastModifiedInApp());
        if (entity.getLastModifiedInCalendar() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getLastModifiedInCalendar());
        }
      }
    };
    this.__deletionAdapterOfEventEntity = new EntityDeletionOrUpdateAdapter<EventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `events` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EventEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfEventEntity = new EntityDeletionOrUpdateAdapter<EventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `events` SET `id` = ?,`title` = ?,`dateStart` = ?,`dateEnd` = ?,`timeStart` = ?,`timeEnd` = ?,`roomId` = ?,`roomName` = ?,`description` = ?,`participants` = ?,`syncToDeviceCalendar` = ?,`deviceCalendarEventId` = ?,`yandexEventId` = ?,`fromDeviceCalendar` = ?,`lastModifiedInApp` = ?,`lastModifiedInCalendar` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EventEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDateStart() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDateStart());
        }
        if (entity.getDateEnd() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDateEnd());
        }
        if (entity.getTimeStart() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTimeStart());
        }
        if (entity.getTimeEnd() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTimeEnd());
        }
        statement.bindLong(7, entity.getRoomId());
        if (entity.getRoomName() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getRoomName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getDescription());
        }
        if (entity.getParticipants() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getParticipants());
        }
        final int _tmp = entity.getSyncToDeviceCalendar() ? 1 : 0;
        statement.bindLong(11, _tmp);
        if (entity.getDeviceCalendarEventId() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getDeviceCalendarEventId());
        }
        if (entity.getYandexEventId() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getYandexEventId());
        }
        final int _tmp_1 = entity.getFromDeviceCalendar() ? 1 : 0;
        statement.bindLong(14, _tmp_1);
        statement.bindLong(15, entity.getLastModifiedInApp());
        if (entity.getLastModifiedInCalendar() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getLastModifiedInCalendar());
        }
        statement.bindLong(17, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteEventById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM events WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertEvent(final EventEntity event, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfEventEntity.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEvent(final EventEntity event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfEventEntity.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateEvent(final EventEntity event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfEventEntity.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEventById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteEventById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteEventById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<EventEntity>> getAllEvents() {
    final String _sql = "SELECT * FROM events ORDER BY dateStart ASC, timeStart ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"events"}, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateStart = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStart");
          final int _cursorIndexOfDateEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEnd");
          final int _cursorIndexOfTimeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStart");
          final int _cursorIndexOfTimeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "timeEnd");
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfRoomName = CursorUtil.getColumnIndexOrThrow(_cursor, "roomName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfParticipants = CursorUtil.getColumnIndexOrThrow(_cursor, "participants");
          final int _cursorIndexOfSyncToDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "syncToDeviceCalendar");
          final int _cursorIndexOfDeviceCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceCalendarEventId");
          final int _cursorIndexOfYandexEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "yandexEventId");
          final int _cursorIndexOfFromDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDeviceCalendar");
          final int _cursorIndexOfLastModifiedInApp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInApp");
          final int _cursorIndexOfLastModifiedInCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInCalendar");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDateStart;
            if (_cursor.isNull(_cursorIndexOfDateStart)) {
              _tmpDateStart = null;
            } else {
              _tmpDateStart = _cursor.getString(_cursorIndexOfDateStart);
            }
            final String _tmpDateEnd;
            if (_cursor.isNull(_cursorIndexOfDateEnd)) {
              _tmpDateEnd = null;
            } else {
              _tmpDateEnd = _cursor.getString(_cursorIndexOfDateEnd);
            }
            final String _tmpTimeStart;
            if (_cursor.isNull(_cursorIndexOfTimeStart)) {
              _tmpTimeStart = null;
            } else {
              _tmpTimeStart = _cursor.getString(_cursorIndexOfTimeStart);
            }
            final String _tmpTimeEnd;
            if (_cursor.isNull(_cursorIndexOfTimeEnd)) {
              _tmpTimeEnd = null;
            } else {
              _tmpTimeEnd = _cursor.getString(_cursorIndexOfTimeEnd);
            }
            final long _tmpRoomId;
            _tmpRoomId = _cursor.getLong(_cursorIndexOfRoomId);
            final String _tmpRoomName;
            if (_cursor.isNull(_cursorIndexOfRoomName)) {
              _tmpRoomName = null;
            } else {
              _tmpRoomName = _cursor.getString(_cursorIndexOfRoomName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpParticipants;
            if (_cursor.isNull(_cursorIndexOfParticipants)) {
              _tmpParticipants = null;
            } else {
              _tmpParticipants = _cursor.getString(_cursorIndexOfParticipants);
            }
            final boolean _tmpSyncToDeviceCalendar;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSyncToDeviceCalendar);
            _tmpSyncToDeviceCalendar = _tmp != 0;
            final Long _tmpDeviceCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfDeviceCalendarEventId)) {
              _tmpDeviceCalendarEventId = null;
            } else {
              _tmpDeviceCalendarEventId = _cursor.getLong(_cursorIndexOfDeviceCalendarEventId);
            }
            final String _tmpYandexEventId;
            if (_cursor.isNull(_cursorIndexOfYandexEventId)) {
              _tmpYandexEventId = null;
            } else {
              _tmpYandexEventId = _cursor.getString(_cursorIndexOfYandexEventId);
            }
            final boolean _tmpFromDeviceCalendar;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfFromDeviceCalendar);
            _tmpFromDeviceCalendar = _tmp_1 != 0;
            final long _tmpLastModifiedInApp;
            _tmpLastModifiedInApp = _cursor.getLong(_cursorIndexOfLastModifiedInApp);
            final Long _tmpLastModifiedInCalendar;
            if (_cursor.isNull(_cursorIndexOfLastModifiedInCalendar)) {
              _tmpLastModifiedInCalendar = null;
            } else {
              _tmpLastModifiedInCalendar = _cursor.getLong(_cursorIndexOfLastModifiedInCalendar);
            }
            _item = new EventEntity(_tmpId,_tmpTitle,_tmpDateStart,_tmpDateEnd,_tmpTimeStart,_tmpTimeEnd,_tmpRoomId,_tmpRoomName,_tmpDescription,_tmpParticipants,_tmpSyncToDeviceCalendar,_tmpDeviceCalendarEventId,_tmpYandexEventId,_tmpFromDeviceCalendar,_tmpLastModifiedInApp,_tmpLastModifiedInCalendar);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<EventEntity>> getEventsByDate(final String date) {
    final String _sql = "SELECT * FROM events WHERE dateStart = ? ORDER BY timeStart ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"events"}, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateStart = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStart");
          final int _cursorIndexOfDateEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEnd");
          final int _cursorIndexOfTimeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStart");
          final int _cursorIndexOfTimeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "timeEnd");
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfRoomName = CursorUtil.getColumnIndexOrThrow(_cursor, "roomName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfParticipants = CursorUtil.getColumnIndexOrThrow(_cursor, "participants");
          final int _cursorIndexOfSyncToDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "syncToDeviceCalendar");
          final int _cursorIndexOfDeviceCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceCalendarEventId");
          final int _cursorIndexOfYandexEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "yandexEventId");
          final int _cursorIndexOfFromDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDeviceCalendar");
          final int _cursorIndexOfLastModifiedInApp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInApp");
          final int _cursorIndexOfLastModifiedInCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInCalendar");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDateStart;
            if (_cursor.isNull(_cursorIndexOfDateStart)) {
              _tmpDateStart = null;
            } else {
              _tmpDateStart = _cursor.getString(_cursorIndexOfDateStart);
            }
            final String _tmpDateEnd;
            if (_cursor.isNull(_cursorIndexOfDateEnd)) {
              _tmpDateEnd = null;
            } else {
              _tmpDateEnd = _cursor.getString(_cursorIndexOfDateEnd);
            }
            final String _tmpTimeStart;
            if (_cursor.isNull(_cursorIndexOfTimeStart)) {
              _tmpTimeStart = null;
            } else {
              _tmpTimeStart = _cursor.getString(_cursorIndexOfTimeStart);
            }
            final String _tmpTimeEnd;
            if (_cursor.isNull(_cursorIndexOfTimeEnd)) {
              _tmpTimeEnd = null;
            } else {
              _tmpTimeEnd = _cursor.getString(_cursorIndexOfTimeEnd);
            }
            final long _tmpRoomId;
            _tmpRoomId = _cursor.getLong(_cursorIndexOfRoomId);
            final String _tmpRoomName;
            if (_cursor.isNull(_cursorIndexOfRoomName)) {
              _tmpRoomName = null;
            } else {
              _tmpRoomName = _cursor.getString(_cursorIndexOfRoomName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpParticipants;
            if (_cursor.isNull(_cursorIndexOfParticipants)) {
              _tmpParticipants = null;
            } else {
              _tmpParticipants = _cursor.getString(_cursorIndexOfParticipants);
            }
            final boolean _tmpSyncToDeviceCalendar;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSyncToDeviceCalendar);
            _tmpSyncToDeviceCalendar = _tmp != 0;
            final Long _tmpDeviceCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfDeviceCalendarEventId)) {
              _tmpDeviceCalendarEventId = null;
            } else {
              _tmpDeviceCalendarEventId = _cursor.getLong(_cursorIndexOfDeviceCalendarEventId);
            }
            final String _tmpYandexEventId;
            if (_cursor.isNull(_cursorIndexOfYandexEventId)) {
              _tmpYandexEventId = null;
            } else {
              _tmpYandexEventId = _cursor.getString(_cursorIndexOfYandexEventId);
            }
            final boolean _tmpFromDeviceCalendar;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfFromDeviceCalendar);
            _tmpFromDeviceCalendar = _tmp_1 != 0;
            final long _tmpLastModifiedInApp;
            _tmpLastModifiedInApp = _cursor.getLong(_cursorIndexOfLastModifiedInApp);
            final Long _tmpLastModifiedInCalendar;
            if (_cursor.isNull(_cursorIndexOfLastModifiedInCalendar)) {
              _tmpLastModifiedInCalendar = null;
            } else {
              _tmpLastModifiedInCalendar = _cursor.getLong(_cursorIndexOfLastModifiedInCalendar);
            }
            _item = new EventEntity(_tmpId,_tmpTitle,_tmpDateStart,_tmpDateEnd,_tmpTimeStart,_tmpTimeEnd,_tmpRoomId,_tmpRoomName,_tmpDescription,_tmpParticipants,_tmpSyncToDeviceCalendar,_tmpDeviceCalendarEventId,_tmpYandexEventId,_tmpFromDeviceCalendar,_tmpLastModifiedInApp,_tmpLastModifiedInCalendar);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<EventEntity>> getEventsByRoom(final long roomId) {
    final String _sql = "SELECT * FROM events WHERE roomId = ? ORDER BY dateStart ASC, timeStart ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, roomId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"events"}, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateStart = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStart");
          final int _cursorIndexOfDateEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEnd");
          final int _cursorIndexOfTimeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStart");
          final int _cursorIndexOfTimeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "timeEnd");
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfRoomName = CursorUtil.getColumnIndexOrThrow(_cursor, "roomName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfParticipants = CursorUtil.getColumnIndexOrThrow(_cursor, "participants");
          final int _cursorIndexOfSyncToDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "syncToDeviceCalendar");
          final int _cursorIndexOfDeviceCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceCalendarEventId");
          final int _cursorIndexOfYandexEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "yandexEventId");
          final int _cursorIndexOfFromDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDeviceCalendar");
          final int _cursorIndexOfLastModifiedInApp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInApp");
          final int _cursorIndexOfLastModifiedInCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInCalendar");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDateStart;
            if (_cursor.isNull(_cursorIndexOfDateStart)) {
              _tmpDateStart = null;
            } else {
              _tmpDateStart = _cursor.getString(_cursorIndexOfDateStart);
            }
            final String _tmpDateEnd;
            if (_cursor.isNull(_cursorIndexOfDateEnd)) {
              _tmpDateEnd = null;
            } else {
              _tmpDateEnd = _cursor.getString(_cursorIndexOfDateEnd);
            }
            final String _tmpTimeStart;
            if (_cursor.isNull(_cursorIndexOfTimeStart)) {
              _tmpTimeStart = null;
            } else {
              _tmpTimeStart = _cursor.getString(_cursorIndexOfTimeStart);
            }
            final String _tmpTimeEnd;
            if (_cursor.isNull(_cursorIndexOfTimeEnd)) {
              _tmpTimeEnd = null;
            } else {
              _tmpTimeEnd = _cursor.getString(_cursorIndexOfTimeEnd);
            }
            final long _tmpRoomId;
            _tmpRoomId = _cursor.getLong(_cursorIndexOfRoomId);
            final String _tmpRoomName;
            if (_cursor.isNull(_cursorIndexOfRoomName)) {
              _tmpRoomName = null;
            } else {
              _tmpRoomName = _cursor.getString(_cursorIndexOfRoomName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpParticipants;
            if (_cursor.isNull(_cursorIndexOfParticipants)) {
              _tmpParticipants = null;
            } else {
              _tmpParticipants = _cursor.getString(_cursorIndexOfParticipants);
            }
            final boolean _tmpSyncToDeviceCalendar;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSyncToDeviceCalendar);
            _tmpSyncToDeviceCalendar = _tmp != 0;
            final Long _tmpDeviceCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfDeviceCalendarEventId)) {
              _tmpDeviceCalendarEventId = null;
            } else {
              _tmpDeviceCalendarEventId = _cursor.getLong(_cursorIndexOfDeviceCalendarEventId);
            }
            final String _tmpYandexEventId;
            if (_cursor.isNull(_cursorIndexOfYandexEventId)) {
              _tmpYandexEventId = null;
            } else {
              _tmpYandexEventId = _cursor.getString(_cursorIndexOfYandexEventId);
            }
            final boolean _tmpFromDeviceCalendar;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfFromDeviceCalendar);
            _tmpFromDeviceCalendar = _tmp_1 != 0;
            final long _tmpLastModifiedInApp;
            _tmpLastModifiedInApp = _cursor.getLong(_cursorIndexOfLastModifiedInApp);
            final Long _tmpLastModifiedInCalendar;
            if (_cursor.isNull(_cursorIndexOfLastModifiedInCalendar)) {
              _tmpLastModifiedInCalendar = null;
            } else {
              _tmpLastModifiedInCalendar = _cursor.getLong(_cursorIndexOfLastModifiedInCalendar);
            }
            _item = new EventEntity(_tmpId,_tmpTitle,_tmpDateStart,_tmpDateEnd,_tmpTimeStart,_tmpTimeEnd,_tmpRoomId,_tmpRoomName,_tmpDescription,_tmpParticipants,_tmpSyncToDeviceCalendar,_tmpDeviceCalendarEventId,_tmpYandexEventId,_tmpFromDeviceCalendar,_tmpLastModifiedInApp,_tmpLastModifiedInCalendar);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<EventEntity>> searchEvents(final String query) {
    final String _sql = "SELECT * FROM events WHERE title LIKE '%' || ? || '%' ORDER BY dateStart ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"events"}, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateStart = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStart");
          final int _cursorIndexOfDateEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEnd");
          final int _cursorIndexOfTimeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStart");
          final int _cursorIndexOfTimeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "timeEnd");
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfRoomName = CursorUtil.getColumnIndexOrThrow(_cursor, "roomName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfParticipants = CursorUtil.getColumnIndexOrThrow(_cursor, "participants");
          final int _cursorIndexOfSyncToDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "syncToDeviceCalendar");
          final int _cursorIndexOfDeviceCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceCalendarEventId");
          final int _cursorIndexOfYandexEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "yandexEventId");
          final int _cursorIndexOfFromDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDeviceCalendar");
          final int _cursorIndexOfLastModifiedInApp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInApp");
          final int _cursorIndexOfLastModifiedInCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInCalendar");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDateStart;
            if (_cursor.isNull(_cursorIndexOfDateStart)) {
              _tmpDateStart = null;
            } else {
              _tmpDateStart = _cursor.getString(_cursorIndexOfDateStart);
            }
            final String _tmpDateEnd;
            if (_cursor.isNull(_cursorIndexOfDateEnd)) {
              _tmpDateEnd = null;
            } else {
              _tmpDateEnd = _cursor.getString(_cursorIndexOfDateEnd);
            }
            final String _tmpTimeStart;
            if (_cursor.isNull(_cursorIndexOfTimeStart)) {
              _tmpTimeStart = null;
            } else {
              _tmpTimeStart = _cursor.getString(_cursorIndexOfTimeStart);
            }
            final String _tmpTimeEnd;
            if (_cursor.isNull(_cursorIndexOfTimeEnd)) {
              _tmpTimeEnd = null;
            } else {
              _tmpTimeEnd = _cursor.getString(_cursorIndexOfTimeEnd);
            }
            final long _tmpRoomId;
            _tmpRoomId = _cursor.getLong(_cursorIndexOfRoomId);
            final String _tmpRoomName;
            if (_cursor.isNull(_cursorIndexOfRoomName)) {
              _tmpRoomName = null;
            } else {
              _tmpRoomName = _cursor.getString(_cursorIndexOfRoomName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpParticipants;
            if (_cursor.isNull(_cursorIndexOfParticipants)) {
              _tmpParticipants = null;
            } else {
              _tmpParticipants = _cursor.getString(_cursorIndexOfParticipants);
            }
            final boolean _tmpSyncToDeviceCalendar;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSyncToDeviceCalendar);
            _tmpSyncToDeviceCalendar = _tmp != 0;
            final Long _tmpDeviceCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfDeviceCalendarEventId)) {
              _tmpDeviceCalendarEventId = null;
            } else {
              _tmpDeviceCalendarEventId = _cursor.getLong(_cursorIndexOfDeviceCalendarEventId);
            }
            final String _tmpYandexEventId;
            if (_cursor.isNull(_cursorIndexOfYandexEventId)) {
              _tmpYandexEventId = null;
            } else {
              _tmpYandexEventId = _cursor.getString(_cursorIndexOfYandexEventId);
            }
            final boolean _tmpFromDeviceCalendar;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfFromDeviceCalendar);
            _tmpFromDeviceCalendar = _tmp_1 != 0;
            final long _tmpLastModifiedInApp;
            _tmpLastModifiedInApp = _cursor.getLong(_cursorIndexOfLastModifiedInApp);
            final Long _tmpLastModifiedInCalendar;
            if (_cursor.isNull(_cursorIndexOfLastModifiedInCalendar)) {
              _tmpLastModifiedInCalendar = null;
            } else {
              _tmpLastModifiedInCalendar = _cursor.getLong(_cursorIndexOfLastModifiedInCalendar);
            }
            _item = new EventEntity(_tmpId,_tmpTitle,_tmpDateStart,_tmpDateEnd,_tmpTimeStart,_tmpTimeEnd,_tmpRoomId,_tmpRoomName,_tmpDescription,_tmpParticipants,_tmpSyncToDeviceCalendar,_tmpDeviceCalendarEventId,_tmpYandexEventId,_tmpFromDeviceCalendar,_tmpLastModifiedInApp,_tmpLastModifiedInCalendar);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object findConflicts(final long roomId, final String date, final String timeStart,
      final String timeEnd, final long excludeId,
      final Continuation<? super List<EventEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM events\n"
            + "        WHERE roomId = ?\n"
            + "          AND dateStart = ?\n"
            + "          AND id != ?\n"
            + "          AND NOT (timeEnd <= ? OR timeStart >= ?)\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 5);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, roomId);
    _argIndex = 2;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    _argIndex = 3;
    _statement.bindLong(_argIndex, excludeId);
    _argIndex = 4;
    if (timeStart == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, timeStart);
    }
    _argIndex = 5;
    if (timeEnd == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, timeEnd);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateStart = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStart");
          final int _cursorIndexOfDateEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEnd");
          final int _cursorIndexOfTimeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStart");
          final int _cursorIndexOfTimeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "timeEnd");
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfRoomName = CursorUtil.getColumnIndexOrThrow(_cursor, "roomName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfParticipants = CursorUtil.getColumnIndexOrThrow(_cursor, "participants");
          final int _cursorIndexOfSyncToDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "syncToDeviceCalendar");
          final int _cursorIndexOfDeviceCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceCalendarEventId");
          final int _cursorIndexOfYandexEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "yandexEventId");
          final int _cursorIndexOfFromDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDeviceCalendar");
          final int _cursorIndexOfLastModifiedInApp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInApp");
          final int _cursorIndexOfLastModifiedInCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInCalendar");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDateStart;
            if (_cursor.isNull(_cursorIndexOfDateStart)) {
              _tmpDateStart = null;
            } else {
              _tmpDateStart = _cursor.getString(_cursorIndexOfDateStart);
            }
            final String _tmpDateEnd;
            if (_cursor.isNull(_cursorIndexOfDateEnd)) {
              _tmpDateEnd = null;
            } else {
              _tmpDateEnd = _cursor.getString(_cursorIndexOfDateEnd);
            }
            final String _tmpTimeStart;
            if (_cursor.isNull(_cursorIndexOfTimeStart)) {
              _tmpTimeStart = null;
            } else {
              _tmpTimeStart = _cursor.getString(_cursorIndexOfTimeStart);
            }
            final String _tmpTimeEnd;
            if (_cursor.isNull(_cursorIndexOfTimeEnd)) {
              _tmpTimeEnd = null;
            } else {
              _tmpTimeEnd = _cursor.getString(_cursorIndexOfTimeEnd);
            }
            final long _tmpRoomId;
            _tmpRoomId = _cursor.getLong(_cursorIndexOfRoomId);
            final String _tmpRoomName;
            if (_cursor.isNull(_cursorIndexOfRoomName)) {
              _tmpRoomName = null;
            } else {
              _tmpRoomName = _cursor.getString(_cursorIndexOfRoomName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpParticipants;
            if (_cursor.isNull(_cursorIndexOfParticipants)) {
              _tmpParticipants = null;
            } else {
              _tmpParticipants = _cursor.getString(_cursorIndexOfParticipants);
            }
            final boolean _tmpSyncToDeviceCalendar;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSyncToDeviceCalendar);
            _tmpSyncToDeviceCalendar = _tmp != 0;
            final Long _tmpDeviceCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfDeviceCalendarEventId)) {
              _tmpDeviceCalendarEventId = null;
            } else {
              _tmpDeviceCalendarEventId = _cursor.getLong(_cursorIndexOfDeviceCalendarEventId);
            }
            final String _tmpYandexEventId;
            if (_cursor.isNull(_cursorIndexOfYandexEventId)) {
              _tmpYandexEventId = null;
            } else {
              _tmpYandexEventId = _cursor.getString(_cursorIndexOfYandexEventId);
            }
            final boolean _tmpFromDeviceCalendar;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfFromDeviceCalendar);
            _tmpFromDeviceCalendar = _tmp_1 != 0;
            final long _tmpLastModifiedInApp;
            _tmpLastModifiedInApp = _cursor.getLong(_cursorIndexOfLastModifiedInApp);
            final Long _tmpLastModifiedInCalendar;
            if (_cursor.isNull(_cursorIndexOfLastModifiedInCalendar)) {
              _tmpLastModifiedInCalendar = null;
            } else {
              _tmpLastModifiedInCalendar = _cursor.getLong(_cursorIndexOfLastModifiedInCalendar);
            }
            _item = new EventEntity(_tmpId,_tmpTitle,_tmpDateStart,_tmpDateEnd,_tmpTimeStart,_tmpTimeEnd,_tmpRoomId,_tmpRoomName,_tmpDescription,_tmpParticipants,_tmpSyncToDeviceCalendar,_tmpDeviceCalendarEventId,_tmpYandexEventId,_tmpFromDeviceCalendar,_tmpLastModifiedInApp,_tmpLastModifiedInCalendar);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllEventsSync(final Continuation<? super List<EventEntity>> $completion) {
    final String _sql = "SELECT * FROM events";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateStart = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStart");
          final int _cursorIndexOfDateEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEnd");
          final int _cursorIndexOfTimeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStart");
          final int _cursorIndexOfTimeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "timeEnd");
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfRoomName = CursorUtil.getColumnIndexOrThrow(_cursor, "roomName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfParticipants = CursorUtil.getColumnIndexOrThrow(_cursor, "participants");
          final int _cursorIndexOfSyncToDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "syncToDeviceCalendar");
          final int _cursorIndexOfDeviceCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceCalendarEventId");
          final int _cursorIndexOfYandexEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "yandexEventId");
          final int _cursorIndexOfFromDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDeviceCalendar");
          final int _cursorIndexOfLastModifiedInApp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInApp");
          final int _cursorIndexOfLastModifiedInCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInCalendar");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDateStart;
            if (_cursor.isNull(_cursorIndexOfDateStart)) {
              _tmpDateStart = null;
            } else {
              _tmpDateStart = _cursor.getString(_cursorIndexOfDateStart);
            }
            final String _tmpDateEnd;
            if (_cursor.isNull(_cursorIndexOfDateEnd)) {
              _tmpDateEnd = null;
            } else {
              _tmpDateEnd = _cursor.getString(_cursorIndexOfDateEnd);
            }
            final String _tmpTimeStart;
            if (_cursor.isNull(_cursorIndexOfTimeStart)) {
              _tmpTimeStart = null;
            } else {
              _tmpTimeStart = _cursor.getString(_cursorIndexOfTimeStart);
            }
            final String _tmpTimeEnd;
            if (_cursor.isNull(_cursorIndexOfTimeEnd)) {
              _tmpTimeEnd = null;
            } else {
              _tmpTimeEnd = _cursor.getString(_cursorIndexOfTimeEnd);
            }
            final long _tmpRoomId;
            _tmpRoomId = _cursor.getLong(_cursorIndexOfRoomId);
            final String _tmpRoomName;
            if (_cursor.isNull(_cursorIndexOfRoomName)) {
              _tmpRoomName = null;
            } else {
              _tmpRoomName = _cursor.getString(_cursorIndexOfRoomName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpParticipants;
            if (_cursor.isNull(_cursorIndexOfParticipants)) {
              _tmpParticipants = null;
            } else {
              _tmpParticipants = _cursor.getString(_cursorIndexOfParticipants);
            }
            final boolean _tmpSyncToDeviceCalendar;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSyncToDeviceCalendar);
            _tmpSyncToDeviceCalendar = _tmp != 0;
            final Long _tmpDeviceCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfDeviceCalendarEventId)) {
              _tmpDeviceCalendarEventId = null;
            } else {
              _tmpDeviceCalendarEventId = _cursor.getLong(_cursorIndexOfDeviceCalendarEventId);
            }
            final String _tmpYandexEventId;
            if (_cursor.isNull(_cursorIndexOfYandexEventId)) {
              _tmpYandexEventId = null;
            } else {
              _tmpYandexEventId = _cursor.getString(_cursorIndexOfYandexEventId);
            }
            final boolean _tmpFromDeviceCalendar;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfFromDeviceCalendar);
            _tmpFromDeviceCalendar = _tmp_1 != 0;
            final long _tmpLastModifiedInApp;
            _tmpLastModifiedInApp = _cursor.getLong(_cursorIndexOfLastModifiedInApp);
            final Long _tmpLastModifiedInCalendar;
            if (_cursor.isNull(_cursorIndexOfLastModifiedInCalendar)) {
              _tmpLastModifiedInCalendar = null;
            } else {
              _tmpLastModifiedInCalendar = _cursor.getLong(_cursorIndexOfLastModifiedInCalendar);
            }
            _item = new EventEntity(_tmpId,_tmpTitle,_tmpDateStart,_tmpDateEnd,_tmpTimeStart,_tmpTimeEnd,_tmpRoomId,_tmpRoomName,_tmpDescription,_tmpParticipants,_tmpSyncToDeviceCalendar,_tmpDeviceCalendarEventId,_tmpYandexEventId,_tmpFromDeviceCalendar,_tmpLastModifiedInApp,_tmpLastModifiedInCalendar);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getSyncableEvents(final Continuation<? super List<EventEntity>> $completion) {
    final String _sql = "SELECT * FROM events WHERE syncToDeviceCalendar = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EventEntity>>() {
      @Override
      @NonNull
      public List<EventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateStart = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStart");
          final int _cursorIndexOfDateEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEnd");
          final int _cursorIndexOfTimeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStart");
          final int _cursorIndexOfTimeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "timeEnd");
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfRoomName = CursorUtil.getColumnIndexOrThrow(_cursor, "roomName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfParticipants = CursorUtil.getColumnIndexOrThrow(_cursor, "participants");
          final int _cursorIndexOfSyncToDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "syncToDeviceCalendar");
          final int _cursorIndexOfDeviceCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceCalendarEventId");
          final int _cursorIndexOfYandexEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "yandexEventId");
          final int _cursorIndexOfFromDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDeviceCalendar");
          final int _cursorIndexOfLastModifiedInApp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInApp");
          final int _cursorIndexOfLastModifiedInCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInCalendar");
          final List<EventEntity> _result = new ArrayList<EventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDateStart;
            if (_cursor.isNull(_cursorIndexOfDateStart)) {
              _tmpDateStart = null;
            } else {
              _tmpDateStart = _cursor.getString(_cursorIndexOfDateStart);
            }
            final String _tmpDateEnd;
            if (_cursor.isNull(_cursorIndexOfDateEnd)) {
              _tmpDateEnd = null;
            } else {
              _tmpDateEnd = _cursor.getString(_cursorIndexOfDateEnd);
            }
            final String _tmpTimeStart;
            if (_cursor.isNull(_cursorIndexOfTimeStart)) {
              _tmpTimeStart = null;
            } else {
              _tmpTimeStart = _cursor.getString(_cursorIndexOfTimeStart);
            }
            final String _tmpTimeEnd;
            if (_cursor.isNull(_cursorIndexOfTimeEnd)) {
              _tmpTimeEnd = null;
            } else {
              _tmpTimeEnd = _cursor.getString(_cursorIndexOfTimeEnd);
            }
            final long _tmpRoomId;
            _tmpRoomId = _cursor.getLong(_cursorIndexOfRoomId);
            final String _tmpRoomName;
            if (_cursor.isNull(_cursorIndexOfRoomName)) {
              _tmpRoomName = null;
            } else {
              _tmpRoomName = _cursor.getString(_cursorIndexOfRoomName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpParticipants;
            if (_cursor.isNull(_cursorIndexOfParticipants)) {
              _tmpParticipants = null;
            } else {
              _tmpParticipants = _cursor.getString(_cursorIndexOfParticipants);
            }
            final boolean _tmpSyncToDeviceCalendar;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSyncToDeviceCalendar);
            _tmpSyncToDeviceCalendar = _tmp != 0;
            final Long _tmpDeviceCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfDeviceCalendarEventId)) {
              _tmpDeviceCalendarEventId = null;
            } else {
              _tmpDeviceCalendarEventId = _cursor.getLong(_cursorIndexOfDeviceCalendarEventId);
            }
            final String _tmpYandexEventId;
            if (_cursor.isNull(_cursorIndexOfYandexEventId)) {
              _tmpYandexEventId = null;
            } else {
              _tmpYandexEventId = _cursor.getString(_cursorIndexOfYandexEventId);
            }
            final boolean _tmpFromDeviceCalendar;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfFromDeviceCalendar);
            _tmpFromDeviceCalendar = _tmp_1 != 0;
            final long _tmpLastModifiedInApp;
            _tmpLastModifiedInApp = _cursor.getLong(_cursorIndexOfLastModifiedInApp);
            final Long _tmpLastModifiedInCalendar;
            if (_cursor.isNull(_cursorIndexOfLastModifiedInCalendar)) {
              _tmpLastModifiedInCalendar = null;
            } else {
              _tmpLastModifiedInCalendar = _cursor.getLong(_cursorIndexOfLastModifiedInCalendar);
            }
            _item = new EventEntity(_tmpId,_tmpTitle,_tmpDateStart,_tmpDateEnd,_tmpTimeStart,_tmpTimeEnd,_tmpRoomId,_tmpRoomName,_tmpDescription,_tmpParticipants,_tmpSyncToDeviceCalendar,_tmpDeviceCalendarEventId,_tmpYandexEventId,_tmpFromDeviceCalendar,_tmpLastModifiedInApp,_tmpLastModifiedInCalendar);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object findByCalendarEventId(final long calEventId,
      final Continuation<? super EventEntity> $completion) {
    final String _sql = "SELECT * FROM events WHERE deviceCalendarEventId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, calEventId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EventEntity>() {
      @Override
      @Nullable
      public EventEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateStart = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStart");
          final int _cursorIndexOfDateEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEnd");
          final int _cursorIndexOfTimeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStart");
          final int _cursorIndexOfTimeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "timeEnd");
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfRoomName = CursorUtil.getColumnIndexOrThrow(_cursor, "roomName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfParticipants = CursorUtil.getColumnIndexOrThrow(_cursor, "participants");
          final int _cursorIndexOfSyncToDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "syncToDeviceCalendar");
          final int _cursorIndexOfDeviceCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceCalendarEventId");
          final int _cursorIndexOfYandexEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "yandexEventId");
          final int _cursorIndexOfFromDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDeviceCalendar");
          final int _cursorIndexOfLastModifiedInApp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInApp");
          final int _cursorIndexOfLastModifiedInCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInCalendar");
          final EventEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDateStart;
            if (_cursor.isNull(_cursorIndexOfDateStart)) {
              _tmpDateStart = null;
            } else {
              _tmpDateStart = _cursor.getString(_cursorIndexOfDateStart);
            }
            final String _tmpDateEnd;
            if (_cursor.isNull(_cursorIndexOfDateEnd)) {
              _tmpDateEnd = null;
            } else {
              _tmpDateEnd = _cursor.getString(_cursorIndexOfDateEnd);
            }
            final String _tmpTimeStart;
            if (_cursor.isNull(_cursorIndexOfTimeStart)) {
              _tmpTimeStart = null;
            } else {
              _tmpTimeStart = _cursor.getString(_cursorIndexOfTimeStart);
            }
            final String _tmpTimeEnd;
            if (_cursor.isNull(_cursorIndexOfTimeEnd)) {
              _tmpTimeEnd = null;
            } else {
              _tmpTimeEnd = _cursor.getString(_cursorIndexOfTimeEnd);
            }
            final long _tmpRoomId;
            _tmpRoomId = _cursor.getLong(_cursorIndexOfRoomId);
            final String _tmpRoomName;
            if (_cursor.isNull(_cursorIndexOfRoomName)) {
              _tmpRoomName = null;
            } else {
              _tmpRoomName = _cursor.getString(_cursorIndexOfRoomName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpParticipants;
            if (_cursor.isNull(_cursorIndexOfParticipants)) {
              _tmpParticipants = null;
            } else {
              _tmpParticipants = _cursor.getString(_cursorIndexOfParticipants);
            }
            final boolean _tmpSyncToDeviceCalendar;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSyncToDeviceCalendar);
            _tmpSyncToDeviceCalendar = _tmp != 0;
            final Long _tmpDeviceCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfDeviceCalendarEventId)) {
              _tmpDeviceCalendarEventId = null;
            } else {
              _tmpDeviceCalendarEventId = _cursor.getLong(_cursorIndexOfDeviceCalendarEventId);
            }
            final String _tmpYandexEventId;
            if (_cursor.isNull(_cursorIndexOfYandexEventId)) {
              _tmpYandexEventId = null;
            } else {
              _tmpYandexEventId = _cursor.getString(_cursorIndexOfYandexEventId);
            }
            final boolean _tmpFromDeviceCalendar;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfFromDeviceCalendar);
            _tmpFromDeviceCalendar = _tmp_1 != 0;
            final long _tmpLastModifiedInApp;
            _tmpLastModifiedInApp = _cursor.getLong(_cursorIndexOfLastModifiedInApp);
            final Long _tmpLastModifiedInCalendar;
            if (_cursor.isNull(_cursorIndexOfLastModifiedInCalendar)) {
              _tmpLastModifiedInCalendar = null;
            } else {
              _tmpLastModifiedInCalendar = _cursor.getLong(_cursorIndexOfLastModifiedInCalendar);
            }
            _result = new EventEntity(_tmpId,_tmpTitle,_tmpDateStart,_tmpDateEnd,_tmpTimeStart,_tmpTimeEnd,_tmpRoomId,_tmpRoomName,_tmpDescription,_tmpParticipants,_tmpSyncToDeviceCalendar,_tmpDeviceCalendarEventId,_tmpYandexEventId,_tmpFromDeviceCalendar,_tmpLastModifiedInApp,_tmpLastModifiedInCalendar);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getDatesWithEvents(final String from, final String to,
      final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT dateStart FROM events WHERE dateStart BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (from == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, from);
    }
    _argIndex = 2;
    if (to == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, to);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getEventById(final long id, final Continuation<? super EventEntity> $completion) {
    final String _sql = "SELECT * FROM events WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EventEntity>() {
      @Override
      @Nullable
      public EventEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDateStart = CursorUtil.getColumnIndexOrThrow(_cursor, "dateStart");
          final int _cursorIndexOfDateEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "dateEnd");
          final int _cursorIndexOfTimeStart = CursorUtil.getColumnIndexOrThrow(_cursor, "timeStart");
          final int _cursorIndexOfTimeEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "timeEnd");
          final int _cursorIndexOfRoomId = CursorUtil.getColumnIndexOrThrow(_cursor, "roomId");
          final int _cursorIndexOfRoomName = CursorUtil.getColumnIndexOrThrow(_cursor, "roomName");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfParticipants = CursorUtil.getColumnIndexOrThrow(_cursor, "participants");
          final int _cursorIndexOfSyncToDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "syncToDeviceCalendar");
          final int _cursorIndexOfDeviceCalendarEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceCalendarEventId");
          final int _cursorIndexOfYandexEventId = CursorUtil.getColumnIndexOrThrow(_cursor, "yandexEventId");
          final int _cursorIndexOfFromDeviceCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "fromDeviceCalendar");
          final int _cursorIndexOfLastModifiedInApp = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInApp");
          final int _cursorIndexOfLastModifiedInCalendar = CursorUtil.getColumnIndexOrThrow(_cursor, "lastModifiedInCalendar");
          final EventEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDateStart;
            if (_cursor.isNull(_cursorIndexOfDateStart)) {
              _tmpDateStart = null;
            } else {
              _tmpDateStart = _cursor.getString(_cursorIndexOfDateStart);
            }
            final String _tmpDateEnd;
            if (_cursor.isNull(_cursorIndexOfDateEnd)) {
              _tmpDateEnd = null;
            } else {
              _tmpDateEnd = _cursor.getString(_cursorIndexOfDateEnd);
            }
            final String _tmpTimeStart;
            if (_cursor.isNull(_cursorIndexOfTimeStart)) {
              _tmpTimeStart = null;
            } else {
              _tmpTimeStart = _cursor.getString(_cursorIndexOfTimeStart);
            }
            final String _tmpTimeEnd;
            if (_cursor.isNull(_cursorIndexOfTimeEnd)) {
              _tmpTimeEnd = null;
            } else {
              _tmpTimeEnd = _cursor.getString(_cursorIndexOfTimeEnd);
            }
            final long _tmpRoomId;
            _tmpRoomId = _cursor.getLong(_cursorIndexOfRoomId);
            final String _tmpRoomName;
            if (_cursor.isNull(_cursorIndexOfRoomName)) {
              _tmpRoomName = null;
            } else {
              _tmpRoomName = _cursor.getString(_cursorIndexOfRoomName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpParticipants;
            if (_cursor.isNull(_cursorIndexOfParticipants)) {
              _tmpParticipants = null;
            } else {
              _tmpParticipants = _cursor.getString(_cursorIndexOfParticipants);
            }
            final boolean _tmpSyncToDeviceCalendar;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSyncToDeviceCalendar);
            _tmpSyncToDeviceCalendar = _tmp != 0;
            final Long _tmpDeviceCalendarEventId;
            if (_cursor.isNull(_cursorIndexOfDeviceCalendarEventId)) {
              _tmpDeviceCalendarEventId = null;
            } else {
              _tmpDeviceCalendarEventId = _cursor.getLong(_cursorIndexOfDeviceCalendarEventId);
            }
            final String _tmpYandexEventId;
            if (_cursor.isNull(_cursorIndexOfYandexEventId)) {
              _tmpYandexEventId = null;
            } else {
              _tmpYandexEventId = _cursor.getString(_cursorIndexOfYandexEventId);
            }
            final boolean _tmpFromDeviceCalendar;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfFromDeviceCalendar);
            _tmpFromDeviceCalendar = _tmp_1 != 0;
            final long _tmpLastModifiedInApp;
            _tmpLastModifiedInApp = _cursor.getLong(_cursorIndexOfLastModifiedInApp);
            final Long _tmpLastModifiedInCalendar;
            if (_cursor.isNull(_cursorIndexOfLastModifiedInCalendar)) {
              _tmpLastModifiedInCalendar = null;
            } else {
              _tmpLastModifiedInCalendar = _cursor.getLong(_cursorIndexOfLastModifiedInCalendar);
            }
            _result = new EventEntity(_tmpId,_tmpTitle,_tmpDateStart,_tmpDateEnd,_tmpTimeStart,_tmpTimeEnd,_tmpRoomId,_tmpRoomName,_tmpDescription,_tmpParticipants,_tmpSyncToDeviceCalendar,_tmpDeviceCalendarEventId,_tmpYandexEventId,_tmpFromDeviceCalendar,_tmpLastModifiedInApp,_tmpLastModifiedInCalendar);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
