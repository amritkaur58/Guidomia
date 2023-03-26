package com.amrit.guidomia.repository

import com.amrit.guidomia.data.database.CarDao
import com.amrit.guidomia.data.database.CarTableDetail
import com.amrit.guidomia.data.model.CarResponse
import com.amrit.guidomia.data.remote.ApiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CarListRepository @Inject constructor(
    private val carDao: CarDao,
    private val apiData: ApiData
) {


    suspend fun getCarList(): List<CarTableDetail>? {
        withContext(Dispatchers.IO)
        {
            delay(1000L)
            val carList = apiData.getCarList()
            val carTableList = arrayListOf<CarTableDetail>()
            carDao.deleteCars()
            carList?.forEach { carData ->
                carTableList.add(carDataModel(carData))
            }
            carDao.insertCars(carTableList)

        }
        return carDao.getCarList()
    }

    private fun carDataModel(carData: CarResponse): CarTableDetail {

        return CarTableDetail(
            consList = carData.consList,
            customerPrice = carData.customerPrice,
            make = carData.make,
            marketPrice = carData.customerPrice,
            model = carData.model,
            prosList = carData.prosList,
            rating = carData.rating
        )
    }
}