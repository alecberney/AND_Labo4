package com.and.and_labo4.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/**
 * Note model
 */
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) var noteId : Long?,
    var state : State,
    var title : String,
    var text : String,
    var creationDate : Calendar,
    var type : Type
) {
    companion object {
        /**
         * Randomizer
         */
        private val rand = Random()

        /**
         * Allowed chars in random text and title
         */
        private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

        /**
         * Generates a random string
         */
        private fun getRandomString(length: Int = 12) : String {
            return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
        }

        /**
         * Generates a random note
         */
        fun generateRandomNote() : Note {
            val _state = if(rand.nextBoolean()) State.IN_PROGRESS else State.DONE
            val _title = getRandomString(4 + rand.nextInt(10))
            val _text = getRandomString(8 + rand.nextInt(20))
            val _type = Type.values()[rand.nextInt(Type.values().size)]

            return Note(
                null,
                _state,
                _title,
                _text,
                Calendar.getInstance(),
                _type
            )
        }

        /**
         * Generate a random schedule
         */
        fun generateRandomSchedule() : Schedule? {
            if(rand.nextBoolean()) return null
            val inThePast = rand.nextDouble() > 0.9
            val sign = if(inThePast) -1 else 1
            return Schedule(null,
                    -1L, //tmp
                    Calendar.getInstance().apply {
                        add(Calendar.MONTH, sign * rand.nextInt(3))
                        add(Calendar.DAY_OF_MONTH, sign * rand.nextInt(30))
                    })
        }
    }
}