package com.psychedelic.weather.mvvm

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.psychedelic.weather.BR
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
            Log.d(
                "onResume", it.HeWeather?.get(0)?.daily_forecast.toString()
                        + " size = " + it.HeWeather?.get(0)?.daily_forecast?.size
            )

            mModel.nowWeather =it.HeWeather?.get(0)?.now?.cond!!.txt
            mModel.nowTem=it.HeWeather?.get(0)?.now!!.tmp.toString()+"°C"
            mModel.cityName = mView!!.getCityName()
            mView?.notifyList(it.HeWeather!!.get(0)!!.daily_forecast)
            Log.d("onResume", it.HeWeather!!.get(0)!!.now!!.tmp.toString())

        }, {

        })


    }

    override fun unsubscribe() {
        repository.unsubscribe()
    }

    inner class Model : BaseObservable(), WeatherItemModel {
        @get:Bindable
        var cityName = "北京市"
        set(value){
            field = value
            notifyPropertyChanged(BR.cityName)
        }

        @get:Bindable
        var nowWeather = "暴雨"
        set(value){
            field = value
            notifyPropertyChanged(BR.nowWeather)
        }
        @get:Bindable
        var nowTem = "30°C"
            set(value){
                field = value
                notifyPropertyChanged(BR.nowTem)
            }
        /** 省份点击 */
        override val onItemClick: (WeatherBean.HeWeatherBean.DailyForecastBean) -> Unit = {

        }
    }
}

interface WeatherView : BaseView {
    fun notifyList(list: ArrayList<WeatherBean.HeWeatherBean.DailyForecastBean>)
    fun getDistrictCode(): String
    fun setNowWeather(weather: String)
    fun setNowTem(tem: String)
    fun getCityName():String
}