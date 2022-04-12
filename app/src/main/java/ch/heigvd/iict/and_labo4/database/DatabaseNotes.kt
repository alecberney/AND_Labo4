package ch.heigvd.iict.and_labo4.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.iict.and_labo4.database.converter.CalendarConverter
import ch.heigvd.iict.and_labo4.database.converter.StateConverter
import ch.heigvd.iict.and_labo4.database.converter.TypeConverter
import ch.heigvd.iict.and_labo4.models.Note
import ch.heigvd.iict.and_labo4.models.Schedule
import kotlin.concurrent.thread

@Database(entities = [Note::class, Schedule::class],
    version = 1,
    exportSchema = true)
@TypeConverters(CalendarConverter::class, StateConverter::class, TypeConverter::class)
abstract class DatabaseNotes : RoomDatabase() {

    abstract fun noteDao() : DAONotes

    abstract fun scheduleDao() : DAONotes

    companion object {
        private var INSTANCE : DatabaseNotes? = null
        fun getDatabase(context: Context) :DatabaseNotes {
            return INSTANCE ?: synchronized(this) {
                // création du singleton, cf slide suivant
                INSTANCE!!
            }
        }
    }


    private class DatabaseNotesCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {database ->
                val isEmpty = database.noteDao().getCount().value == 0L
                if(isEmpty) {
                    thread {
                        // TODO when the database is created for the 1st time, we can, for example, populate it
                        // TODO should be done in a thread
                    }
                }
            }
        }
        override fun onOpen(db: SupportSQLiteDatabase) { super.onOpen(db) }
        override fun onDestructiveMigration(db: SupportSQLiteDatabase) { super.onDestructiveMigration(db) }
    }

}