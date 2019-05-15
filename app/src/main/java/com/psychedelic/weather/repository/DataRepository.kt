package com.psychedelic.weather.repository

import android.util.Log
import cn.wj.android.common.tools.asyncSchedulers
import com.psychedelic.weather.base.BaseRepository
import com.psychedelic.weather.entity.CityEntity
import com.psychedelic.weather.entity.DistrictEntity
import com.psychedelic.weather.entity.ProvinceEntity
import com.psychedelic.weather.entity.WeatherBean
import com.psychedelic.weather.expanding.netSubscribe
import com.psychedelic.weather.utils.qwe
import io.reactivex.schedulers.Schedulers


/**
 * 数据仓库
 */
class DataRepository : BaseRepository() {
    fun getProvince(success: (ArrayList<ProvinceEntity>) -> Unit,
                    failed: ((Throwable) -> Unit)? = null) {
        Log.d("BaseRepository","getProvinceList")
        addDisposable(
                webService.getProvinceList()
                    .asyncSchedulers()
                    .subscribeOn(Schedulers.io())
                    .netSubscribe(success, failed)
        )
    }

    fun getCity(provinceCode:String, success: (ArrayList<CityEntity>) -> Unit, failed: ((Throwable) -> Unit)?=null){
        Log.d("BaseRepository","getCityList"+provinceCode)
        addDisposable(
            webService.getCities(provinceCode)
                .asyncSchedulers()
                .subscribeOn(Schedulers.io())
                .netSubscribe(success,failed)
        )

    }
    fun getDistrict(provinceCode:String,cityCode:String, success: (ArrayList<DistrictEntity>) -> Unit, failed: ((Throwable) -> Unit)?=null){
        addDisposable(
            webService.getDistricts(provinceCode,cityCode)
                .asyncSchedulers()
                .subscribeOn(Schedulers.io())
                .netSubscribe(success,failed)
        )

    }

    fun getWeather(districtCode: String, success: (WeatherBean) -> Unit, failed: ((Throwable) -> Unit)?=null){
        addDisposable(
            webService.getWeathers(districtCode)
//            webService.getWeathers()
                .asyncSchedulers()
                .subscribeOn(Schedulers.io())
                .netSubscribe(success,failed)
        )
    }

}