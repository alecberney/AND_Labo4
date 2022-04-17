package com.and.and_labo4.database.converter

import androidx.room.TypeConverter
import com.and.and_labo4.models.State

/**
 * Converter of enum state for database
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class StateConverter {

    /**
     * From string to enum value.
     */
    @TypeConverter
    fun toState(stateString: String) = enumValueOf<State>(stateString)

    /**
     * From enum value to string
     */
    @TypeConverter
    fun fromState(state: State) = state.name
}