package com.example.roombooking.presentation.calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f0\u000e\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0017\u001a\u00020\u0015H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\u0015H\u0016J\"\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0017\u001a\u00020\u00152\b\u0010\u001c\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J,\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\u00072\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010#\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/example/roombooking/presentation/calendar/CalendarGridAdapter;", "Landroid/widget/BaseAdapter;", "context", "Landroid/content/Context;", "selectedDate", "Ljava/time/LocalDate;", "currentMonth", "Ljava/time/YearMonth;", "datesWithEvents", "", "", "viewMode", "Lcom/example/roombooking/presentation/calendar/CalendarViewMode;", "onDateClick", "Lkotlin/Function1;", "", "(Landroid/content/Context;Ljava/time/LocalDate;Ljava/time/YearMonth;Ljava/util/Set;Lcom/example/roombooking/presentation/calendar/CalendarViewMode;Lkotlin/jvm/functions/Function1;)V", "days", "", "buildDays", "getCount", "", "getItem", "pos", "getItemId", "", "getView", "Landroid/view/View;", "convertView", "parent", "Landroid/view/ViewGroup;", "update", "selected", "month", "events", "mode", "app_debug"})
public final class CalendarGridAdapter extends android.widget.BaseAdapter {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private java.time.LocalDate selectedDate;
    @org.jetbrains.annotations.NotNull()
    private java.time.YearMonth currentMonth;
    @org.jetbrains.annotations.NotNull()
    private java.util.Set<java.lang.String> datesWithEvents;
    @org.jetbrains.annotations.NotNull()
    private com.example.roombooking.presentation.calendar.CalendarViewMode viewMode;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<java.time.LocalDate, kotlin.Unit> onDateClick = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<java.time.LocalDate> days;
    
    public CalendarGridAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate selectedDate, @org.jetbrains.annotations.NotNull()
    java.time.YearMonth currentMonth, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> datesWithEvents, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.presentation.calendar.CalendarViewMode viewMode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.time.LocalDate, kotlin.Unit> onDateClick) {
        super();
    }
    
    public final void update(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate selected, @org.jetbrains.annotations.NotNull()
    java.time.YearMonth month, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> events, @org.jetbrains.annotations.NotNull()
    com.example.roombooking.presentation.calendar.CalendarViewMode mode) {
    }
    
    private final java.util.List<java.time.LocalDate> buildDays() {
        return null;
    }
    
    @java.lang.Override()
    public int getCount() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.time.LocalDate getItem(int pos) {
        return null;
    }
    
    @java.lang.Override()
    public long getItemId(int pos) {
        return 0L;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View getView(int pos, @org.jetbrains.annotations.Nullable()
    android.view.View convertView, @org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent) {
        return null;
    }
}