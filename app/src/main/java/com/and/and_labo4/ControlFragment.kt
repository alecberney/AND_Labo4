package com.and.and_labo4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels


/**
 * Controller fragment with a delete all notes button and generate a random note button
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class ControlFragment : Fragment() {

    private val notesViewModel : NotesViewModel by activityViewModels{
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
        return inflater.inflate(R.layout.fragment_control, container, false)
    }

    /**
     * When view is created, set interactions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Counter
        val counter = view.findViewById<TextView>(R.id.counter)
        notesViewModel.countNotes.observe(viewLifecycleOwner) { value ->
            counter.text = "$value"
        }

        // Generate a random note
        view.findViewById<Button>(R.id.btn_generate).setOnClickListener {
            notesViewModel.generateANote()
        }

        // Delete all notes
        view.findViewById<Button>(R.id.btn_delete).setOnClickListener {
            notesViewModel.deleteAllNote()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        @JvmStatic
        fun newInstance() =
            ControlFragment()
    }
}