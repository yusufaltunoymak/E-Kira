package com.example.splash

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Advent(
     @ColumnInfo(name= "Aciklama")
     val Aciklama: String,
     @ColumnInfo(name= "Baslik")
     val Baslik: String,
     @ColumnInfo(name= "Kirafiyati")
     val Kirafiyati: String,
     @ColumnInfo(name= "Periyot")
     val Periyot: String,
     @ColumnInfo(name= "cities")
     val cities: String,
     @ColumnInfo(name= "districts")
     val districts: String,
     @ColumnInfo(name= "downloadUrl")
     val downloadUrl: String,
     @ColumnInfo(name= "quarters")
     val quarters: String,
     @ColumnInfo(name= "towns")
     val towns: String
             ) : Serializable {
                  @PrimaryKey(autoGenerate = true)
                  var id = 0
}