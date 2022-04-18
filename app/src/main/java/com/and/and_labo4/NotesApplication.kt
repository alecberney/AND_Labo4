package com.and.and_labo4

import android.app.Application
import com.and.and_labo4.database.NotesDatabase
import com.and.and_labo4.database.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Custom application object
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class NotesApplication : Application() {

    /**
     * Repository coroutine scope.
     */
    private val applicationScope = CoroutineScope(SupervisorJob())

    /**
     * Notes Repository unique instance.
     */
    val repository by lazy {
        val database = NotesDatabase.getDatabase(this)
        NotesRepository(database.notesDao(), applicationScope)
    }
}