package com.and.and_labo4.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.and.and_labo4.models.Note
import com.and.and_labo4.models.NoteAndSchedule
import com.and.and_labo4.models.Schedule

/**
 * Dao to notes database access
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
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

    @Query("DELETE FROM schedule")
    fun deleteAllSchedules()
}