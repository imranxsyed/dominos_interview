package com.photo.compare.dominostraining

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.photo.compare.dominostraining.model.City

class CityAdapter(val dataset : MutableList<City> = mutableListOf())
    :RecyclerView.Adapter<CityAdapter.CityViewHolder>()
{
    class CityViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        private lateinit var cityName : TextView
        fun onBind(city : City){
            cityName = view.findViewById(R.id.tv_city_name)
            cityName.text = city.cityName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_viewholder, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.onBind(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}