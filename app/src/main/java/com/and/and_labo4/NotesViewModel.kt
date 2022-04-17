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
     * Attribute that correspond to all notes.
     */
    val allNotes : LiveData<List<NoteAndSchedule>> get() = _allNotes

    /**
     * Private attribute storing all notes. "Observed" by attribute allNotes.
     */
    private val _allNotes = repository.allNotes

    /**
     * Attribute that correspond to the notes count.
     */
    val countNotes : LiveData<Long> get() = _countNotes

    /**
     * Attribute that correspond to the notes count. "Observed" by attribute countNotes.
     */
    private val _countNotes = repository.notesCount

    /**
     * Generates a random note and add it in the database.
     */
    fun generateANote() {

        // Generating random noteAndSchedule
        repository.insertNoteWithSchedule(Note.generateRandomNote(), Note.generateRandomSchedule())
        val newNote = Note.generateRandomNote()
    }

    /**
     * Deletes all note (in database too).
     */
    fun deleteAllNote() {
        repository.deleteAll()
    }
}