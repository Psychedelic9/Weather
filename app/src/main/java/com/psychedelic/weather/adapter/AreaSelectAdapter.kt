package com.psychedelic.weather.adapter

import android.widget.TextView
import cn.wj.android.common.adapter.recyclerview.BaseRvViewHolder
import com.psychedelic.weather.R
import com.psychedelic.weather.entity.CityEntity
import com.psychedelic.weather.entity.DistrictEntity
import com.psychedelic.weather.entity.ProvinceEntity

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/10 14:14
 *@UpdateDate: 2019/5/10 14:14
 *@Description:
 *@ClassName: AreaSelectAdapter
 */
class AreaSelectAdapter
    : SimpleRvAdapter<ProvinceEntity>(R.layout.area_select_recycle_view_item) {
    override fun convert(holder: BaseRvViewHolder<ProvinceEntity>, entity: ProvinceEntity) {
        super.convert(holder, entity)
        holder.itemView.findViewById<TextView>(R.id.area_select_item).setText(entity.name)
    }
}

class CitySelectAdapter
    : SimpleRvAdapter<CityEntity>(R.layout.city_select_recycle_view_item) {
    override fun convert(holder: BaseRvViewHolder<CityEntity>, entity: CityEntity) {
        super.convert(holder, entity)
        holder.itemView.findViewById<TextView>(R.id.city_select_item).setText(entity.name)
    }

}
class DistrictSelectAdapter
    : SimpleRvAdapter<DistrictEntity>(R.layout.district_select_recycle_view_item) {
    override fun convert(holder: BaseRvViewHolder<DistrictEntity>, entity: DistrictEntity) {
        super.convert(holder,entity)
        holder.itemView.findViewById<TextView>(R.id.district_select_item).setText(entity.name)
    }
}