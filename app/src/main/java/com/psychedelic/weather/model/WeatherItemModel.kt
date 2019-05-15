package com.psychedelic.weather.model

import com.psychedelic.weather.entity.ProvinceEntity
import com.psychedelic.weather.entity.WeatherBean

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/14 9:54
 *@UpdateDate: 2019/5/14 9:54
 *@Description:
 *@ClassName: WeatherItemModel
 */
interface WeatherItemModel {
    val onItemClick: (WeatherBean.HeWeatherBean.DailyForecastBean) -> Unit
}