package com.and.and_labo4.database

import com.and.and_labo4.models.Note
import com.and.and_labo4.models.Schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Repository of notes
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class NotesRepository(private val noteDAO: NotesDAO, private val scope: CoroutineScope) {

    /**
     * Live data on all notes
     */
    val allNotes = noteDAO.getAllNoteAndSchedule()

    /**
     * Live data on notes count
     */
    val notesCount = noteDAO.getCount()

    /**
     * Inserts a note and his associated schedule. Schedule may be null.
     */
    fun insertNoteWithSchedule(note : Note, schedule : Schedule?) {
        scope.launch(Dispatchers.IO) {
            val noteId = noteDAO.insert(note)
            if (schedule != null) {
                schedule.ownerId = noteId
                noteDAO.insert(schedule)
            }
        }
    }

    /**
     * Deletes all notes and associated schedules
     */
    fun deleteAll() {
        scope.launch(Dispatchers.IO) {
            noteDAO.deleteAllSchedules()
            noteDAO.deleteAllNotes()
        }
    }
}