package com.and.and_labo4.models

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Relation object between a note and its potential schedule
 */
data class NoteAndSchedule (
    @Embedded val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "ownerId"
    )
    val schedule: Schedule?
)
