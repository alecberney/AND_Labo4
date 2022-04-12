package ch.heigvd.iict.and_labo4.database.converter

import androidx.room.TypeConverter
import ch.heigvd.iict.and_labo4.models.Type

class TypeConverter {
    @TypeConverter
    fun toState(typeString: String) = enumValueOf<Type>(typeString)

    @TypeConverter
    fun fromState(type: Type) = type.name
}