package com.psychedelic.weather.mvvm

import android.util.Log
import androidx.databinding.BaseObservable
import com.psychedelic.weather.base.mvvm.BaseView
import com.psychedelic.weather.base.mvvm.BaseViewModel
import com.psychedelic.weather.entity.WeatherBean
import com.psychedelic.weather.model.WeatherItemModel
import com.psychedelic.weather.repository.DataRepository
import kotlin.collections.ArrayList

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/14 9:51
 *@UpdateDate: 2019/5/14 9:51
 *@Description:
 *@ClassName: Weather
 */
class WeatherViewMode(private val repository: DataRepository) : BaseViewModel<WeatherView>() {
    val mModel = Model()
    var mDistrictCode = ""
    override fun onCreate() {
        super.onCreate()
    }


    override fun onResume() {
        super.onResume()
        mDistrictCode = mView!!.getDistrictCode()
        Log.d("Weather", "DistrictId = " + mDistrictCode)
        repository.getWeather(mDistrictCode, {
            Log.d("onResume", it.HeWeather?.get(0)?.daily_forecast.toString()
            +" size = "+it.HeWeather?.get(0)?.daily_forecast?.size
            )
            mView?.notifyList(it.HeWeather!!.get(0)!!.daily_forecast)
        }, {

        })
    }

    override fun unsubscribe() {
        repository.unsubscribe()
    }

    inner class Model : BaseObservable(), WeatherItemModel {

        /** 省份点击 */
        override val onItemClick: (WeatherBean.HeWeatherBean) -> Unit = {

        }

    }
}

interface WeatherView : BaseView {
    fun notifyList(list: ArrayList<WeatherBean.HeWeatherBean.DailyForecastBean>)
    fun getDistrictCode(): String
}