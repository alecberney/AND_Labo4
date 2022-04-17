package com.and.and_labo4.database

import com.and.and_labo4.models.Note
import com.and.and_labo4.models.Schedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesRepository(private val noteDAO: NotesDAO, private val scope: CoroutineScope) {

    val allNotes = noteDAO.getAllNoteAndSchedule()

    val notesCount = noteDAO.getCount()

    fun insertNoteWithSchedule(note : Note, schedule : Schedule?) {
        scope.launch(Dispatchers.IO) {
            val noteId = noteDAO.insert(note)
            if (schedule != null) {
                schedule.ownerId = noteId
                noteDAO.insert(schedule)
            }
        }
    }

    fun deleteAll() {
        scope.launch(Dispatchers.IO) {
            noteDAO.deleteAllSchedules()
            noteDAO.deleteAllNotes()
        }
    }
}