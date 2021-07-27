package com.photo.compare.dominostraining.model

import com.google.gson.annotations.SerializedName

class City {
    /*
      {
        "city": "New York",
        "growth_from_2000_to_2013": "4.8%",
        "latitude": 40.7127837,
        "longitude": -74.0059413,
        "population": "8405837",
        "rank": "1",
        "state": "New York"
    },
     */
    @SerializedName("city")
    var cityName : String? = null
    @SerializedName("growth_from_2000_to_2013")
    var growth : String?=  null
    var latitude : Double = -1.0
    var longitude : Double = -1.0
    var population : Int = -1
    var rank : Int = -1
    var state : String? = null


}