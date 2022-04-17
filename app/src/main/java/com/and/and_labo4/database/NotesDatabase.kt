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

@Database(entities = [Note::class, Schedule::class],
    version = 1,
    exportSchema = true)
@TypeConverters(CalendarConverter::class, StateConverter::class, TypeConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao() : NotesDAO

    companion object {
        private var INSTANCE : NotesDatabase? = null
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
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {database ->
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
        override fun onOpen(db: SupportSQLiteDatabase) { super.onOpen(db) }
        override fun onDestructiveMigration(db: SupportSQLiteDatabase) { super.onDestructiveMigration(db) }
    }

}