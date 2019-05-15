package com.psychedelic.weather.model

import com.psychedelic.weather.entity.ProvinceEntity


interface StoresItemModel {

    val onItemClick: (ProvinceEntity) -> Unit
}