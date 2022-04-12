package ch.heigvd.iict.and_labo4.database.converter

import androidx.room.TypeConverter
import java.util.Calendar
import java.util.Date

class CalendarConverter {
    @TypeConverter
    fun toCalendar(dateLong: Long) =
        Calendar.getInstance().apply {
            time = Date(dateLong)
        }
    @TypeConverter
    fun fromCalendar(date: Calendar) =
        date.time.time
}