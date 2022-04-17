package com.and.and_labo4

import android.app.Application
import android.content.res.Configuration
import com.and.and_labo4.database.NotesDatabase
import com.and.and_labo4.database.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    val repository by lazy {
        val database = NotesDatabase.getDatabase(this)
        NotesRepository(database.notesDao(), applicationScope)
    }


    override fun onCreate() {
        super.onCreate()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
    override fun onLowMemory() {
        super.onLowMemory()
    }
}