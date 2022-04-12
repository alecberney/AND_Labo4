package ch.heigvd.iict.and_labo4.database.converter

import androidx.room.TypeConverter
import ch.heigvd.iict.and_labo4.models.State

class StateConverter {
    @TypeConverter
    fun toState(stateString: String) = enumValueOf<State>(stateString)

    @TypeConverter
    fun fromState(state: State) = state.name
}