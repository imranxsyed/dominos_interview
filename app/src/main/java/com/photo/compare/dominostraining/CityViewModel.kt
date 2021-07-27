package com.photo.compare.dominostraining

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.photo.compare.dominostraining.model.City
import com.photo.compare.dominostraining.network.CitiesServiceManager
import com.photo.compare.dominostraining.network.DominoRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityViewModel() : ViewModel() {
    val errorSignal = MutableLiveData<Boolean>(null)
    val cities = MutableLiveData<List<City>>(null)

    fun getCities(targetLong : Double, targetLat : Double){
        DominoRetrofit.getRetrofitClient("https://gist.githubusercontent.com")
            .create(CitiesServiceManager::class.java)
            .getCities()
            .enqueue(object : Callback<List<City>>{
                override fun onResponse(call: Call<List<City>>, response: Response<List<City>>) {
                    val list = mutableListOf<City>()
                    if (response.isSuccessful){
                        response.body()?.run {
                            list.addAll(this)
                        }
                    }
                    if (list.isNotEmpty()){
                        //filter the values
                        list.filter { distance(targetLat, targetLong, it.latitude, it.longitude) < 500 }
                        if (list.isNotEmpty()){
                            cities.postValue(list)
                        }
                        else{
                            errorSignal.postValue(true)
                        }
                    }
                    else{
                        errorSignal.postValue(true)
                    }
                }

                override fun onFailure(call: Call<List<City>>, t: Throwable) {
                    errorSignal.postValue(true)
                }

            })
    }

    /** calculates the distance between two locations in MILES  */
    private fun distance(
        lat1: Double,
        lng1: Double,
        lat2: Double,
        lng2: Double
    ): Double {
        val earthRadius = 3958.75 // in miles, change to 6371 for kilometer output
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val sindLat = Math.sin(dLat / 2)
        val sindLng = Math.sin(dLng / 2)
        val a = Math.pow(sindLat, 2.0) + (Math.pow(sindLng, 2.0)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(
            Math.toRadians(
                lat2
            )
        ))
        val c =
            2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadius * c // output distance, in MILES
    }
}