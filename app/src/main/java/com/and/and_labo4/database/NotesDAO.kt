package com.and.and_labo4.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.and.and_labo4.models.Note
import com.and.and_labo4.models.NoteAndSchedule
import com.and.and_labo4.models.Schedule

@Dao
interface NotesDAO {

    /****************************************
     *           Note and schedule          *
     ****************************************/

    @Transaction
    @Query("SELECT * FROM note")
    fun getAllNoteAndSchedule() : LiveData<List<NoteAndSchedule>>

    /****************************************
     *                 Note                 *
     ****************************************/

    @Insert
    fun insert(note : Note) : Long

    @Insert
    fun insertAll(vararg note : Note)

    @Update
    fun update(note : Note)

    @Delete
    fun delete(note : Note)

    @Query("DELETE FROM note")
    fun deleteAllNotes()

    @Query("SELECT COUNT(*) FROM note")
    fun getCount() : LiveData<Long>

    /****************************************
     *               Schedule               *
     ****************************************/

    @Insert
    fun insert(schedule : Schedule) : Long

    @Update
    fun update(schedule : Schedule)

    @Delete
    fun delete(schedule : Schedule)

    @Query("DELETE FROM schedule")
    fun deleteAllSchedules()
}