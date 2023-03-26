package com.amrit.guidomia.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CarTableDetail::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class CarDatabase : RoomDatabase() {

    companion object {
        private var DB_INSTANCE: CarDatabase? = null

        fun getCarInstance(context: Context): CarDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "CAR_DB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }


    }

    abstract fun getCarDao(): CarDao
}