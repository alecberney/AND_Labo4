package ch.heigvd.iict.and_labo4.database

import ch.heigvd.iict.and_labo4.models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryNotes(private val noteDAO: DAONotes, private val scope: CoroutineScope) {
    val allNotes = noteDAO.getAllNotes()
    val notesCount = noteDAO.getCount()
    fun insertPersons(vararg notes: Note) {
        // on doit effectuer les opérations sur les données dans un thread, ou une coroutine
        scope.launch(Dispatchers.IO) {
            //* est le spread operator, pour passer un array à une fonction acceptant un vararg
            noteDAO.insertAll(*notes)
        }
    }

    fun deleteAll() {
        scope.launch(Dispatchers.IO) {
            noteDAO.deleteAll()
        }
    }
}