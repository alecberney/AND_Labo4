package com.and.and_labo4.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.and.and_labo4.database.converter.CalendarConverter
import com.and.and_labo4.database.converter.StateConverter
import com.and.and_labo4.database.converter.TypeConverter
import com.and.and_labo4.models.Note
import com.and.and_labo4.models.Schedule
import kotlin.concurrent.thread

/**
 * Notes database
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
@Database(entities = [Note::class, Schedule::class],
    version = 1,
    exportSchema = true)
@TypeConverters(CalendarConverter::class, StateConverter::class, TypeConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    /**
     * Returns the dao to access database.
     */
    abstract fun notesDao() : NotesDAO

    companion object {
        /**
         * Unique database instance.
         */
        private var INSTANCE : NotesDatabase? = null

        /**
         * Returns the database unique instance.
         */
        fun getDatabase(context: Context) :NotesDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    NotesDatabase::class.java, "notesdatabase.db")
                    .addCallback(NotesDatabaseCallback())
                    .build()
                INSTANCE!!
            }

        }
    }


    private class NotesDatabaseCallback : RoomDatabase.Callback() {

        /**
         * Callback function when database is accessed
         */
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {database ->

                // If no notes, generate 5 notes
                val isEmpty = database.notesDao().getCount().value == null
                if(isEmpty) {
                    thread {
                       for (i in 1 .. 5) {
                           val note = Note.generateRandomNote()
                           val schedule = Note.generateRandomSchedule()
                           val noteId = database.notesDao().insert(note)
                           if (schedule != null) {
                               schedule.ownerId = noteId
                               database.notesDao().insert(schedule)
                           }
                        }
                    }
                }
            }
        }
    }
}