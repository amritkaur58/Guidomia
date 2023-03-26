package com.amrit.guidomia.repository

import com.amrit.guidomia.data.database.CarTableDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CarData @Inject constructor(
    private val carListRepository: CarListRepository
) {
    fun getCarList(): Flow<CarListStateUpdate> = flow {
        emit(CarListStateUpdate.CheckProgress)
        try {

            val carList = carListRepository.getCarList()
            carList?.let {
                emit(CarListStateUpdate.Response(it))
            }
        } catch (e: Exception) {
            emit(CarListStateUpdate.Error("Data not uploaded"))
        }

    }


}

open class CarListStateUpdate {
    object CheckProgress : CarListStateUpdate()
    class Response(val data: List<CarTableDetail>?) : CarListStateUpdate()
    class Error(val message: String) : CarListStateUpdate()
}