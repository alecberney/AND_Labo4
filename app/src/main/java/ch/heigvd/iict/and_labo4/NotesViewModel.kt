package ch.heigvd.iict.and_labo4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.heigvd.iict.and_labo4.models.Note
import ch.heigvd.iict.and_labo4.models.NoteAndSchedule

/**
 * View model for NotesActivity
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class NotesViewModel : ViewModel() {

    /**
     * Attribute that correspond to all notes.
     */
    val allNotes : LiveData<List<NoteAndSchedule>> get() = _allNotes

    /**
     * Private attribute storing all notes. "Observed" by attribute allNotes.
     */
    private val _allNotes = MutableLiveData<List<NoteAndSchedule>>(listOf<NoteAndSchedule>())

    /**
     * Attribute that correspond to the notes count.
     */
    val countNotes : LiveData<Int> get() = _countNotes

    /**
     * Attribute that correspond to the notes count. "Observed" by attribute countNotes.
     */
    private val _countNotes = MutableLiveData<Int>(0)

    /**
     * Generates a random note and add it in the database.
     */
    fun generateANote() {

        // counter increment
        val c = _countNotes.value!!
        _countNotes.postValue(c+1)

        // Generating random noteAndSchedule
        val newNoteAndSchedule = NoteAndSchedule(Note.generateRandomNote(), Note.generateRandomSchedule())

        // TODO add note in db
    }

    /**
     * Deletes all note (in database too).
     */
    fun deleteAllNote() {

        // counter reset
        _countNotes.postValue(0)

        // emptying list
        _allNotes.postValue(listOf<NoteAndSchedule>())

        // TODO Empty database
    }
}