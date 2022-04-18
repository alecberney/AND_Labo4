package com.and.and_labo4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.and.and_labo4.database.NotesRepository

/**
 * Factory for notes view model creation
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {

    /**
     * Create an instance of NotesViewModel
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}