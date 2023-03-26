package com.amrit.guidomia.data.remote

import android.app.Application
import android.content.Context
import com.amrit.guidomia.data.database.CarDao
import com.amrit.guidomia.data.database.CarDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceClass {

    @Provides
    @Singleton
    fun getCarDataBase(context: Context): CarDatabase {
        return CarDatabase.getCarInstance(context)
    }

    @Provides
    @Singleton
    fun getCarDao(carDatabase: CarDatabase): CarDao {
        return carDatabase.getCarDao()
    }

    @Provides
    @Singleton
    fun applicationContext(application: Application): Context = application.applicationContext


}