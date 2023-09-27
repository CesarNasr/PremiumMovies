package com.example.premiummovies.data.localdatasource.database

import androidx.room.TypeConverter
import com.example.premiummovies.data.localdatasource.entity.movies.MovieDataEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MovieDataTypeConverter {

    @TypeConverter
    fun fromCountryLangList(value: List<MovieDataEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<MovieDataEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCountryLangList(value: String): List<MovieDataEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<MovieDataEntity>>() {}.type
        return gson.fromJson(value, type)
    }
}


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