package com.psychedelic.weather.model

import com.psychedelic.weather.entity.CityEntity
import com.psychedelic.weather.entity.DistrictEntity

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/13 17:14
 *@UpdateDate: 2019/5/13 17:14
 *@Description:
 *@ClassName: DistrictsItemModel
 */
interface DistrictsItemModel {
    val onItemClick: (DistrictEntity) -> Unit
}