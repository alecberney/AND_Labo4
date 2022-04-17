package com.and.and_labo4.database.converter

import androidx.room.TypeConverter
import java.util.Calendar
import java.util.Date

/**
 * Converter of calendar for database
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class CalendarConverter {

    /**
     * From long to calendar
     */
    @TypeConverter
    fun toCalendar(dateLong: Long) =
        Calendar.getInstance().apply {
            time = Date(dateLong)
        }

    /**
     * From calendar to long
     */
    @TypeConverter
    fun fromCalendar(date: Calendar) =
        date.time.time
}