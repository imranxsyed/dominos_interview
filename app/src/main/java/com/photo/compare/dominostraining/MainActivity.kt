package com.photo.compare.dominostraining

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var searchButton : Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var error: TextView
    private lateinit var cities : RecyclerView
    private val mViewModel by viewModels<CityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initObservers()
    }
    fun initData(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        error = findViewById(R.id.tv_error)
        searchButton = findViewById(R.id.btn_search_cities)
        cities = findViewById(R.id.rv_cities)
        searchButton.setOnClickListener{
            //TODO Internet availability
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), 124);
                return@setOnClickListener
            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location == null){
                        cities.visibility = View.GONE
                        error.visibility = View.VISIBLE
                    }
                    else{
                        val lat = location.latitude
                        val long = location.longitude
                        mViewModel.getCities(long, lat)

                    }
                }


        }
    }
    fun initObservers(){
        mViewModel.errorSignal.observe(this){
            it?.run {
                if (this){
                    error.visibility = View.VISIBLE
                    cities.visibility = View.GONE
                }
                else{
                    error.visibility = View.GONE
                    cities.visibility = View.VISIBLE
                }
            }
        }
        mViewModel.cities.observe(this){
            it?.run {
                cities.layoutManager = LinearLayoutManager(this@MainActivity)
                cities.adapter = CityAdapter(it.toMutableList())
            }

        }
    }
}