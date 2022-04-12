package ch.heigvd.iict.and_labo4.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ch.heigvd.iict.and_labo4.models.Note
import ch.heigvd.iict.and_labo4.models.Schedule

@Dao
interface DAONotes {
    @Insert
    fun insert(note : Note) : Long

    @Insert
    fun insertAll(vararg note : Note)

    @Update
    fun update(note : Note)

    @Delete
    fun delete(note : Note)

    @Query("DELETE FROM note")
    fun deleteAll()

    @Query("SELECT * FROM note")
    fun getAllNotes() : LiveData<List<Note>>

    @Query("SELECT COUNT(*) FROM note")
    fun getCount() : LiveData<Long>

    @Insert
    fun insert(schedule : Schedule) : Long

    @Update
    fun update(schedule : Schedule)

    @Delete
    fun delete(schedule : Schedule)
}