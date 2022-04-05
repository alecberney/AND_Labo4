package ch.heigvd.iict.and_labo4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.heigvd.iict.and.labo4.models.NoteAndSchedule

/**
 * View model for NotesActivity
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class NotesViewModel : ViewModel() {

//    /**
//     * Attribute that correspond to all notes.
//     */
//    val allNotes : LiveData<List<NoteAndSchedule>> get() = _allNotes
//
//    /**
//     * Private attribute storing all notes. "Observed" by attribute allNotes.
//     */
//    private val _allNotes = MutableLiveData<List<NoteAndSchedule>>(listOf<NoteAndSchedule>())
//
//    /**
//     * Attribute that correspond to the notes count.
//     */
//    val countNotes : LiveData<Int> get() = _countNotes
//
//    /**
//     * Attribute that correspond to the notes count. "Observed" by attribute countNotes.
//     */
//    private val _countNotes = MutableLiveData<Int>(0)
//
//
//    private val _counter = MutableLiveData(0)
//    fun increment() {
//        val c = _counter.value!!
//        _counter.postValue(c+1)
//    }
//
//    /**
//     * Generates a random note and add it in the database.
//     */
//    fun generateANote() { /* création d’une Note aléatoire et insertion dans base de données */ }
//
//    /**
//     * Deletes all note (in database too).
//     */
//    fun deleteAllNote() { /* suppression de toutes les Notes de la base de données */ }

}