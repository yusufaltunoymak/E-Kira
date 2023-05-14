package com.example.splash

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AdventDao {
    @Query("SELECT * FROM Advent")
    fun getAll(): List<Advent>
    @Delete
    fun delete(advent: Advent)

    @Insert
    fun insert(advent: Advent)
    @Query("SELECT EXISTS (SELECT 1 FROM Advent WHERE Baslik = :baslik)")
    fun isAdventExist(baslik: String): Boolean


}