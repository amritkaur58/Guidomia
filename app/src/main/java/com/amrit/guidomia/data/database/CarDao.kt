package com.amrit.guidomia.data.database

import androidx.room.*


@Dao
interface CarDao {

    @Query("SELECT * FROM carList")
    fun getCarList(): List<CarTableDetail>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCars(listings: List<CarTableDetail>)

    @Query("DELETE FROM carList")
    fun deleteCars()
}

class Converter {
    @TypeConverter
    fun fromString(stringList: String): List<String> {
        return stringList.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}