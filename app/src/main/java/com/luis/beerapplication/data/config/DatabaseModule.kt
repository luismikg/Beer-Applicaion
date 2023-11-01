package com.luis.beerapplication.data.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.luis.beerapplication.utils.TypeConverter
import com.luis.beerapplication.domain.ItemDao
import com.luis.beerapplication.data.model.FavoriteItemEntity
import com.luis.beerapplication.data.model.ItemEntity

@Database(
    entities = [ItemEntity::class, FavoriteItemEntity::class], version = 1
)

@TypeConverters(TypeConverter::class)
abstract class DatabaseModule : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}