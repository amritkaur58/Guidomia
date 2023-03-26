package com.amrit.guidomia.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carList")
data class CarTableDetail(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("carId")
    var carId: Int = 0,
    @ColumnInfo(name = "consList")
    val consList: List<String>,
    @ColumnInfo(name = "customerPrice")
    val customerPrice: Int,
    @ColumnInfo(name = "make")
    val make: String,
    @ColumnInfo(name = "marketPrice")
    val marketPrice: Int,
    @ColumnInfo(name = "model")
    val model: String,
    @ColumnInfo(name = "prosList")
    val prosList: List<String>,
    @ColumnInfo(name = "rating")
    val rating: Int
) {
}