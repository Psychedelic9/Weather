package com.psychedelic.weather.utils

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.psychedelic.weather.entity.CityEntity
import com.psychedelic.weather.entity.DistrictEntity
import com.psychedelic.weather.entity.ProvinceEntity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/9 15:50
 *@UpdateDate: 2019/5/9 15:50
 *@Description:
 *@ClassName: Utility
 */
object Utility {
    fun handleProvinceResponse(response: String): List<ProvinceEntity> {
        Log.d("handleProvinceResponse","response="+response)
        val provinces = mutableListOf<ProvinceEntity>()
        if (!TextUtils.isEmpty(response)) {
            try {
                //将Json数组转化为Kotlin数组形式
                var allProvinces = JSONArray(response)
                //对数组循环处理，每次创建一个Province对象
                for (i in 0..allProvinces.length() - 1) {
                    val provinceObject = allProvinces.getJSONObject(i)
                    val province = ProvinceEntity(
                        name = provinceObject.getString("name"),
                        id = provinceObject.getString("id")
                    )
                    provinces.add(provinces.size, province)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return provinces
    }

    fun handleCityResponse(response: String, provinceCode: String): List<CityEntity> {
        var cities = mutableListOf<CityEntity>()

        return cities
    }

    fun handleDistrictResponse(response: String, cityCode: String): List<DistrictEntity> {
        var districts = mutableListOf<DistrictEntity>()
//        if (!TextUtils.isEmpty(response)) {
//            try {
//                val allDistrict = JSONArray(response)
//                for (i in 0..allDistrict.length() - 1) {
//                    val districtObject = allDistrict.getJSONObject(i)
//                    val district = DistrictEntity(
//                        districtName = districtObject.getString("name"),
//                        districtCode = districtObject.getString("id"), cityCode = cityCode
//                    )
//                    districts.add(district)
//                }
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//        }
        return districts
    }

    fun handleWeatherResponse(response: String): Weather? {
        try {
            val jsonObject = JSONObject(response)
            val jsonArray = jsonObject.getJSONArray("HeWeather")
            val weatherContent = jsonArray.getJSONObject(0).toString()
            return Gson().fromJson(weatherContent, Weather::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}

//data class Weather(
//    var status: String, var basic: Basic, var aqi: AQI, var now: Now
//    , var uggestion: Suggestion, var forecastList: List<Forecast>
//)
class Weather {
    var status: String? = null
    var basic: Basic? = null
    var aqi: AQI? = null
    var now: Now? = null
    var suggestion: Suggestion? = null
}

class AQI {
    var city: AQICity? = null

    inner class AQICity {
        var aqi: String? = null
        var pm25: String? = null

    }
}

class Now {
    @SerializedName("tmp")
    var temperature: String? = null
    @SerializedName("cond")
    var more: More? = null

    inner class More {
        @SerializedName("txt")
        var info: String? = null
    }
}

class Suggestion {
    @SerializedName("comf")
    var omfort: Comfort? = null
    @SerializedName("cw")
    var carWash: CarWash? = null
    var sport: Sport? = null

    inner class Comfort {
        @SerializedName("txt")
        var info: String? = null
    }

    inner class CarWash {
        @SerializedName("txt")
        var info: String? = null
    }

    inner class Sport {
        @SerializedName("txt")
        var info: String? = null
    }
}

class Basic {
    @SerializedName("city")
    var cityName: String? = null
    @SerializedName("id")
    var weatherId: String? = null
    var update: Update? = null

    inner class Update {
        @SerializedName("loc")
        var updateTime: String? = null
    }
}

object DataSupport {
    //从InputStream对象读取数据，并转化为ByteArray
    private fun getBytesByInputStream(content: InputStream): ByteArray {
        var bytes: ByteArray? = null
        val bis = BufferedInputStream(content)
        val baos = ByteArrayOutputStream()
        val bos = BufferedOutputStream(baos)
        val buffer = ByteArray(1024 * 8)
        var length = 0
        try {
            while (true) {
                length = bis.read(buffer)
                if (length < 0)
                    break
                bos.write(buffer, 0, length)
            }
            bos.flush()
            bytes = baos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                bis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return bytes!!
    }

    private fun getServerContent(urlStr: String): String {
        var url = URL(urlStr)
        var conn = url.openConnection() as HttpURLConnection
        //HttpURLConnection默认就是GET请求，可以不写
        conn.requestMethod = "GET"
        conn.doInput = true
        conn.useCaches = false
        val content = conn.inputStream
        var responseBody = getBytesByInputStream(content)
        var str = kotlin.text.String(responseBody, Charset.forName("utf-8"))
        return str
    }

    //获取省列表
    fun getProvinces(provinces: (List<ProvinceEntity>) -> Unit) {
        Thread() {
            var content = getServerContent("https://geekori.com/api/china")
            //将省JSON数据转化成List<ProvinceEntity>对象返回
            var provinces = Utility.handleProvinceResponse(content)
            provinces(provinces)
        }.start()
    }

    //获取市列表
    fun getCities(provinceCode: String, cities: (List<CityEntity>) -> Unit) {
        Thread() {
            var content = getServerContent("https://geekori.com/api/china/${provinceCode}")
            var cities = Utility.handleCityResponse(content, provinceCode)
            cities(cities)
        }.start()
    }

    //获取区县列表
    fun getDistricts(provinceCode: String, cityCode: String, districts: (List<DistrictEntity>) -> Unit) {
        Thread() {
            var content = getServerContent("https://geekori.com/china/${provinceCode}/${cityCode}")
            var districts = Utility.handleDistrictResponse(content, cityCode)
            districts(districts)
        }.start()
    }

}