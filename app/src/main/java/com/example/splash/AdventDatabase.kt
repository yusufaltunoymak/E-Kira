package com.example.splash

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Advent::class], version = 2)
abstract class AdventDatabase : RoomDatabase() {
    abstract fun adventDao(): AdventDao
}