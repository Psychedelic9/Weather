package com.psychedelic.weather.net


import com.psychedelic.weather.entity.CityEntity
import com.psychedelic.weather.entity.DistrictEntity
import com.psychedelic.weather.entity.ProvinceEntity
import com.psychedelic.weather.entity.WeatherBean
import com.psychedelic.weather.utils.qwe
import io.reactivex.Observable
import retrofit2.http.*

/**
 * 网络请求接口
 */
interface WebService {
    @GET("china/")
//     fun getProvinceList(): Observable<NetResult<ArrayList<ProvinceEntity>>>
     fun getProvinceList(): Observable<ArrayList<ProvinceEntity>>

    @GET("china/{provinceName}/")
    fun getCities(@Path("provinceName")provinceName:String):Observable<ArrayList<CityEntity>>

    @GET("china/{provinceName}/{cityName}")
    fun getDistricts(@Path("provinceName")provinceName: String
                     ,@Path("cityName")cityName:String):Observable<ArrayList<DistrictEntity>>
    @GET("weather/")
    fun getWeathers(@Query("id")districtCode:String):Observable<WeatherBean>
//    @GET("weather/?id=140522")
//    fun getWeathers():Observable<WeatherBean>
}