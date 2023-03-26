package com.amrit.guidomia.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.amrit.guidomia.R
import com.amrit.guidomia.data.database.CarTableDetail
import com.amrit.guidomia.databinding.ActivityMainBinding
import com.amrit.guidomia.repository.CarListStateUpdate
import com.amrit.guidomia.ui.Adapter.CarAdapter
import com.amrit.guidomia.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var carAdapter: CarAdapter
    private var carList: List<CarTableDetail> = listOf()
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.makeET.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                try {
                    carAdapter.fromMake = true
                    carAdapter.fromModel = false
                    carAdapter.filter.filter(query)
                    carAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return false

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    carAdapter.filter.filter(newText)
                    carAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return false
            }

        })

        binding.modelET.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(query: String?): Boolean {
                try {
                    carAdapter.fromMake = false
                    carAdapter.fromModel = true
                    carAdapter.filter.filter(query)
                    carAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return false

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextChange(newText: String?): Boolean {
                try {
                    carAdapter.fromModel = true
                    carAdapter.fromMake = false
                    carAdapter.filter.filter(newText)
                    carAdapter.notifyDataSetChanged()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return false
            }

        })

        setRecyclerView()
        getData()

    }

    private fun getData() {
        viewModel.carResponse.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).onEach {
            when (it) {

                is CarListStateUpdate.CheckProgress ->
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.updating),
                        Toast.LENGTH_SHORT
                    ).show()
                is CarListStateUpdate.Response -> {
                    it.data?.let { carData ->
                        setDataToView(carData)
                    }
                }
                is CarListStateUpdate.Error ->
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
            }

        }.launchIn(lifecycleScope)

    }

    private fun setDataToView(carData: List<CarTableDetail>) {

        carList = carData
        setRecyclerView()
        carAdapter.addData(carData)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView() {
        binding.carRV.layoutManager = LinearLayoutManager(this)
        carAdapter = CarAdapter()
        with(binding.carRV)
        {
            adapter = carAdapter
            carAdapter.notifyDataSetChanged()
        }
    }


}