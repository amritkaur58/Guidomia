package com.amrit.guidomia.data.remote

import android.content.Context
import com.amrit.guidomia.data.model.CarResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class ApiData @Inject constructor(
    private val context: Context
) {
    fun getCarList(): ArrayList<CarResponse>? {
        val jsonFile: String
        try {
            jsonFile =
                context.assets.open("jsonfile.json").bufferedReader().use { it.readText() }

        } catch (e: Exception) {
            println("Error $e")
            return null
        }

        val carList = object : TypeToken<List<CarResponse>>() {}.type
        return Gson().fromJson(jsonFile, carList)

    }
}