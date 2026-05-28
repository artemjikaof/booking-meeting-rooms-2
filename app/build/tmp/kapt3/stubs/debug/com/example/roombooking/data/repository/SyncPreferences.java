package com.example.roombooking.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R0\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R$\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0005\u001a\u00020\u00198F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0016\u0010\u001f\u001a\n !*\u0004\u0018\u00010 0 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R(\u0010\"\u001a\u0004\u0018\u00010\u00192\b\u0010\u0005\u001a\u0004\u0018\u00010\u00198F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R$\u0010\'\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b(\u0010\t\"\u0004\b)\u0010\u000b\u00a8\u0006*"}, d2 = {"Lcom/example/roombooking/data/repository/SyncPreferences;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "value", "", "backgroundSyncEnabled", "getBackgroundSyncEnabled", "()Z", "setBackgroundSyncEnabled", "(Z)V", "", "backgroundSyncIntervalHours", "getBackgroundSyncIntervalHours", "()I", "setBackgroundSyncIntervalHours", "(I)V", "", "", "filterTags", "getFilterTags", "()Ljava/util/List;", "setFilterTags", "(Ljava/util/List;)V", "", "lastSyncTime", "getLastSyncTime", "()J", "setLastSyncTime", "(J)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "selectedCalendarId", "getSelectedCalendarId", "()Ljava/lang/Long;", "setSelectedCalendarId", "(Ljava/lang/Long;)V", "syncEnabled", "getSyncEnabled", "setSyncEnabled", "app_debug"})
public final class SyncPreferences {
    private final android.content.SharedPreferences prefs = null;
    
    @javax.inject.Inject()
    public SyncPreferences(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final boolean getSyncEnabled() {
        return false;
    }
    
    public final void setSyncEnabled(boolean value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getSelectedCalendarId() {
        return null;
    }
    
    public final void setSelectedCalendarId(@org.jetbrains.annotations.Nullable()
    java.lang.Long value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getFilterTags() {
        return null;
    }
    
    public final void setFilterTags(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> value) {
    }
    
    public final long getLastSyncTime() {
        return 0L;
    }
    
    public final void setLastSyncTime(long value) {
    }
    
    public final boolean getBackgroundSyncEnabled() {
        return false;
    }
    
    public final void setBackgroundSyncEnabled(boolean value) {
    }
    
    public final int getBackgroundSyncIntervalHours() {
        return 0;
    }
    
    public final void setBackgroundSyncIntervalHours(int value) {
    }
}