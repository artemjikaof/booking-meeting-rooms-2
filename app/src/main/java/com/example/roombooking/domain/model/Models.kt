package com.example.roombooking.domain.model

data class Room(
    val id: Long = 0,
    val name: String,
    val capacity: Int,
    val description: String = "",
    val photoUris: List<String> = emptyList()
)

data class Event(
    val id: Long = 0,
    val title: String,
    val dateStart: String,       // "YYYY-MM-DD"
    val dateEnd: String,
    val timeStart: String,       // "HH:mm"
    val timeEnd: String,
    val roomId: Long,
    val roomName: String = "",
    val description: String = "",
    val participants: String = "",
    val syncToDeviceCalendar: Boolean = false,
    val deviceCalendarEventId: Long? = null,
    val fromDeviceCalendar: Boolean = false,
    val lastModifiedInApp: Long = System.currentTimeMillis(),
    val lastModifiedInCalendar: Long? = null
) {
    val isPast: Boolean get() {
        val today = java.time.LocalDate.now().toString()
        return dateEnd < today || (dateEnd == today && timeEnd < java.time.LocalTime.now().toString())
    }
    val isToday: Boolean get() = dateStart == java.time.LocalDate.now().toString()
}
