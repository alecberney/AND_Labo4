package com.and.and_labo4

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.and.and_labo4.database.NotesRepository
import com.and.and_labo4.models.Note
import com.and.and_labo4.models.NoteAndSchedule

/**
 * View model for NotesActivity
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class NotesViewModel(private val repository: NotesRepository) : ViewModel() {

    /**
     * Attribute that corresponds to all notes.
     */
    val allNotes : LiveData<List<NoteAndSchedule>> get() = repository.allNotes

    /**
     * Attribute that correspond to the notes count.
     */
    val countNotes : LiveData<Long> get() = repository.notesCount

    /**
     * Generates a random note and add it in the database.
     */
    fun generateANote() {
        repository.insertNoteWithSchedule(Note.generateRandomNote(), Note.generateRandomSchedule())
    }

    /**
     * Deletes all note (in database too).
     */
    fun deleteAllNote() {
        repository.deleteAll()
    }
}