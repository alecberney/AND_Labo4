package com.and.and_labo4.database.converter

import androidx.room.TypeConverter
import com.and.and_labo4.models.Type

/**
 * Converter of enum type for database
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class TypeConverter {

    /**
     * From string to enum value.
     */
    @TypeConverter
    fun toState(typeString: String) = enumValueOf<Type>(typeString)

    /**
     * From enum value to string
     */
    @TypeConverter
    fun fromState(type: Type) = type.name
}