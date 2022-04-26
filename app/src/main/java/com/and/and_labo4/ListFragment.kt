package com.and.and_labo4

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.and.and_labo4.adapter.NotesAdapter


/**
 * Controller fragment with a delete all notes button and generate a random note button
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class ListFragment : Fragment() {

    /**
     * RecyclerView note adapter.
     */
    private val adapter = NotesAdapter()

    /**
     * SharedPreferences to save the sorting order.
     */
    private lateinit var prefs: SharedPreferences

    /**
     * View model of notes.
     */
    private val notesViewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as NotesApplication).repository)
    }

    /**
     * When view is being created, inflates layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    /**
     * When view is created, setup recycler view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = this.activity?.getPreferences(Context.MODE_PRIVATE) ?: return

        // GUI linking
        val recycler = view.findViewById<RecyclerView>(R.id.list)

        // Initialization
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(view.context)

        notesViewModel.allNotes.observe(viewLifecycleOwner) { value ->
            adapter.items = value

            val sortPrefs: String? = prefs.getString(SORT_PREF, null)

            if (sortPrefs == SORT_PREF_ETA) {
                sortByETA()
            } else if (sortPrefs == SORT_PREF_DATE) {
                sortByCreationDate()
            }

        }
    }

    /**
     * Sort the displayed list by creation date
     */
    fun sortByCreationDate() {
        adapter.sortByCreationDate()
        prefs.edit {
            putString(SORT_PREF, SORT_PREF_DATE)
        }
    }

    /**
     * Sort the displayed list by ETA
     */
    fun sortByETA() {
        adapter.sortByETA()
        prefs.edit {
            putString(SORT_PREF, SORT_PREF_ETA)
        }
    }

    companion object {

        val SORT_PREF = "sort"
        val SORT_PREF_DATE = "date"
        val SORT_PREF_ETA = "eta"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        @JvmStatic
        fun newInstance() =
            ListFragment()
    }
}