package com.and.and_labo4

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.edit

/**
 * Notes activity, display a list and controls such as creation and sort.
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class NotesActivity : AppCompatActivity() {



    private lateinit var listFragment: ListFragment

    /**
     * Notes viewmodel
     */
    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as NotesApplication).repository)
    }

    /**
     * On create, set layout.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        listFragment = supportFragmentManager.findFragmentById(R.id.fragment_list) as ListFragment

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
        return when (item.itemId) {
            R.id.menu_sort_creation_date -> {
                listFragment.sortByCreationDate()
                true
            }
            R.id.menu_sort_eta -> {
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
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}