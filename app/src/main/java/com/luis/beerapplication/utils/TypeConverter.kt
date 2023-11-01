package com.luis.beerapplication.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luis.beerapplication.data.model.BoilVolume
import com.luis.beerapplication.data.model.Fermentation
import com.luis.beerapplication.data.model.Ingredients
import com.luis.beerapplication.data.model.Method
import com.luis.beerapplication.data.model.Temp
import com.luis.beerapplication.data.model.Volume
import java.lang.reflect.Type
import java.util.Collections

class TypeConverter {

    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<String>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<String?>?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun someBoilVolumeObjectToString(boilVolume: BoilVolume): String? {
        return gson.toJson(boilVolume)
    }

    @TypeConverter
    fun someStringToBoilVolumeObject(data: String?): BoilVolume {
        if (data == null) {
            return BoilVolume("", 0)
        }
        return gson.fromJson(data, BoilVolume::class.java)
    }

    @TypeConverter
    fun someIngredientsObjectToString(ingredients: Ingredients): String? {
        return gson.toJson(ingredients)
    }

    @TypeConverter
    fun someStringToIngredientsObject(data: String?): Ingredients? {
        if (data == null) {
            return Ingredients(listOf(), listOf(), "")
        }
        return gson.fromJson(data, Ingredients::class.java)
    }

    @TypeConverter
    fun someMethodObjectToString(method: Method): String? {
        return gson.toJson(method)
    }

    @TypeConverter
    fun someStringToMethodObject(data: String?): Method {
        if (data == null) {
            return Method(Fermentation(Temp("", 0)), listOf(), Any())
        }
        return gson.fromJson(data, Method::class.java)
    }

    @TypeConverter
    fun someVolumeObjectToString(volume: Volume): String? {
        return gson.toJson(volume)
    }

    @TypeConverter
    fun someStringToVolumeObject(data: String?): Volume {
        if (data == null) {
            return Volume("", 0)
        }
        return gson.fromJson(data, Volume::class.java)
    }
}