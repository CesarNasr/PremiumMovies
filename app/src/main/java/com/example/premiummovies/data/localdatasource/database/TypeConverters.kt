package com.example.premiummovies.data.localdatasource.database

import androidx.room.TypeConverter
/**
 * Type converters are required when inserting complex (nested) object into room database (in case we don't want to create a third independent tables for that data)
 */

class IntegerTypeConverter {
    @TypeConverter
    fun fromListIntToString(intList: List<Int>): String = intList.toString()
    @TypeConverter
    fun toListIntFromString(stringList: String): List<Int> {
        val result = ArrayList<Int>()
        val split =stringList.replace("[","").replace("]","").replace(" ","").split(",")
        for (n in split) {
            try {
                result.add(n.toInt())
            } catch (e: Exception) {

            }
        }
        return result
    }
}