package com.example.musica

import androidx.room.TypeConverter
import java.util.*

class DataTypeConverter {

    @TypeConverter
    fun toArray(date: Long) = Date(date)
}