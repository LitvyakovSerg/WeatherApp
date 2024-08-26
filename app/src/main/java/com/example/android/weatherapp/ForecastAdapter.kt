package com.example.android.weatherapp

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.weatherapp.model.forecast.ForecastResult

class ForecastAdapter(private val fragmentContext: Context, private val forecastList: List<ForecastResult>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

   class ViewHolder(private val binding: ItemForecastBinding, private val context: Context) {

   }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}