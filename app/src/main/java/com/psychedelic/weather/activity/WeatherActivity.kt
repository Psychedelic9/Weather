package com.psychedelic.weather.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.psychedelic.weather.R
import com.psychedelic.weather.adapter.ForecastAdapter
import com.psychedelic.weather.base.ui.BaseActivity
import com.psychedelic.weather.databinding.ActivityWeatherBinding
import com.psychedelic.weather.entity.WeatherBean
import com.psychedelic.weather.mvvm.WeatherView
import com.psychedelic.weather.mvvm.WeatherViewMode
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.ArrayList

class WeatherActivity : BaseActivity<WeatherViewMode,WeatherView, ActivityWeatherBinding>(),WeatherView {
    private var mCityCode = ""
    private var mDistrictCode = "110100"
    private val mAdapter: ForecastAdapter by inject()

    val instance by lazy { this }

    override val mViewModel: WeatherViewMode by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        mCityCode = instance.intent.getStringExtra("CityCode")
        mDistrictCode = instance.intent.getStringExtra("DistrictCode")
        mBinding.model = mViewModel.mModel
        mAdapter.mModel= mViewModel.mModel
        mBinding.includeForecast.forecastRecycleView.layoutManager = LinearLayoutManager(this)
        mBinding.includeForecast.forecastRecycleView.adapter = mAdapter

    }
    override fun notifyList(list: ArrayList<WeatherBean.HeWeatherBean>) {
        mAdapter.refresh(list)
    }

    override fun getDistrictCode(): String {
        return mDistrictCode
    }
}
