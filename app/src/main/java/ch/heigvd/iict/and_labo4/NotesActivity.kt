package ch.heigvd.iict.and_labo4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ch.heigvd.iict.and_labo4.NotesViewModel

/**
 * NotesActivity
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class NotesActivity : AppCompatActivity() {
    private val _notesViewModel = NotesViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_notes, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_sort_creation_date -> { _notesViewModel.allNotes.sort }
            R.id.menu_sort_eta -> { _notesViewModel.allNotes.sort }
            R.id.menu_btn_generate_note -> { _notesViewModel.generateANote() }
            R.id.menu_btn_delete_all_note -> { _notesViewModel.deleteAllNote() }
            else -> super.onOptionsItemSelected(item)
        }
    }*/
}