package com.amrit.guidomia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amrit.guidomia.repository.CarData
import com.amrit.guidomia.repository.CarListStateUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val carData: CarData) : ViewModel() {
    val getValue = MutableStateFlow<CarListStateUpdate>(CarListStateUpdate.CheckProgress)
    val carResponse: StateFlow<CarListStateUpdate> = getValue

    init {
        getCarList()
    }

    private fun getCarList() {

        carData.getCarList().onEach { checkResponse ->
            getValue.value = checkResponse
        }.launchIn(viewModelScope)
    }
}