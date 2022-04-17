package com.and.and_labo4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.and.and_labo4.database.NotesRepository

class NotesViewModelFactory(private val repository: NotesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}