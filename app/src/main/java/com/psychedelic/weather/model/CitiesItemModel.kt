package com.psychedelic.weather.model

import com.psychedelic.weather.entity.CityEntity

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/13 15:41
 *@UpdateDate: 2019/5/13 15:41
 *@Description:
 *@ClassName: CitiesItemModel
 */
interface CitiesItemModel{
    val onItemClick: (CityEntity) -> Unit

}