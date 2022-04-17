package com.and.and_labo4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment

class NotesActivity : AppCompatActivity() {

    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as NotesApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
    }

    /**
     * Add custom menu to layout.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_notes, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    /**
     * Handling menu item clicked.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_sort_creation_date -> {
                val listFragment: ListFragment =
                    supportFragmentManager.findFragmentById(R.id.fragment_list) as ListFragment
                listFragment.sortByCreationDate()
                true
            }
            R.id.menu_sort_eta -> {
                val listFragment: ListFragment =
                    supportFragmentManager.findFragmentById(R.id.fragment_list) as ListFragment
                listFragment.sortByETA()
                true
            }

            // Not present in size:large menu_notes
            R.id.menu_btn_generate_note -> {
                notesViewModel.generateANote()
                true
            }

            // Not present in size:large menu_notes
            R.id.menu_btn_delete_all_note -> {
                notesViewModel.deleteAllNote()
                true }

            else -> super.onOptionsItemSelected(item)
        }
    }
}