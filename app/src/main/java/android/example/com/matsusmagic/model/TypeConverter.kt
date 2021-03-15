package android.example.com.matsusmagic.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    @TypeConverter
    fun fromString(value: String?): Map<String?, String?>? {
        val mapType = object : TypeToken<Map<String, String>>(){}.type
        return Gson().fromJson(value, mapType)
    }
    @TypeConverter
    fun fromMap(value: Map<String?, String?>?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }
}