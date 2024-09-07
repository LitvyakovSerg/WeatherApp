package com.example.android.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android.weatherapp.databinding.ActivityMainBinding
import com.example.android.weatherapp.databinding.FragmentForecastBinding
import com.example.android.weatherapp.databinding.FragmentWeatherBinding
import com.example.android.weatherapp.databinding.ItemForecastBinding
import com.example.android.weatherapp.model.forecast.ForecastResult
import com.example.android.weatherapp.repository.WeatherRepositoryImpl
import java.text.SimpleDateFormat
import java.util.Locale


class ForecastAdapter(private val fragmentContext: Context, private val forecastList: List<ForecastResult>) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

   class ViewHolder(private val binding: ItemForecastBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(forecast: ForecastResult) {

            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("d MMMM HH:mm", Locale.getDefault())

            val date = inputDateFormat.parse(forecast.date)
            val outputDate = date?.let { outputDateFormat.format(it) }

            binding.itemRecyclerDate.text = "Date: ${outputDate}"
            binding.itemRecyclerTemp.text = "Temp: ${forecast.temp}"
            binding.itemRecyclerDescription.text = "Weather: ${forecast.description}"

            when (forecast.main) {
                WeatherRepositoryImpl.WEATHER_TYPE_CLEAR -> {
                    binding.weatherImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.sun
                        )
                    )
                }
                WeatherRepositoryImpl.WEATHER_TYPE_CLOUDS -> {
                    binding.weatherImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.cloudy
                        )
                    )
                }
                WeatherRepositoryImpl.WEATHER_TYPE_RAIN -> {
                    binding.weatherImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.heavy_rain
                        )
                    )
                }
                WeatherRepositoryImpl.WEATHER_TYPE_SNOW -> {
                    binding.weatherImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.snow
                        )
                    )
                }
                WeatherRepositoryImpl.WEATHER_TYPE_THUNDERSTORM -> {
                    binding.weatherImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.thunderstorm
                        )
                    )
                }
                else -> {
                    binding.weatherImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.mist
                        )
                    )
                }
            }
        }
   }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastAdapter.ViewHolder {
        val binding = ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, fragmentContext)
    }

    override fun onBindViewHolder(holder: ForecastAdapter.ViewHolder, position: Int) {
       val forecast = forecastList[position]
       holder.bindItem(forecast)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}