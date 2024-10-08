package com.example.android.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.weatherapp.databinding.FragmentForecastBinding
import com.example.android.weatherapp.model.forecast.ForecastResult
import com.example.android.weatherapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentForecastBinding? = null
    private val binding get() = _binding

    private lateinit var adapter: ForecastAdapter
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForecastBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding?.forecastRecycler?.layoutManager = layoutManager

        mainViewModel.forecastResult.observe(viewLifecycleOwner, Observer {
            val forecastResultList = mutableListOf<ForecastResult>()
            it.list.forEach {
                forecastResultList.add(
                    ForecastResult(
                        main = it.weather.first().main,
                        description = it.weather.first().description,
                        temp = it.main.temp.toString(),
                        date = it.dt_txt
                    )
                )
            }

            adapter = ForecastAdapter(requireContext(), forecastResultList)
            binding?.forecastRecycler?.adapter = adapter
        })
    }

    companion object {
        fun newInstance() = ForecastFragment()
    }
}