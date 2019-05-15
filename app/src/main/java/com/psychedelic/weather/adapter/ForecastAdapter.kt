package com.psychedelic.weather.adapter

import android.widget.TextView
import cn.wj.android.common.adapter.recyclerview.BaseRvViewHolder
import com.psychedelic.weather.R
import com.psychedelic.weather.entity.WeatherBean

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/14 14:19
 *@UpdateDate: 2019/5/14 14:19
 *@Description:
 *@ClassName: ForecastAdapter
 */
class ForecastAdapter :
    SimpleRvAdapter<WeatherBean.HeWeatherBean.DailyForecastBean>(R.layout.forecast_recycle_item){
    override fun convert(holder: BaseRvViewHolder<WeatherBean.HeWeatherBean.DailyForecastBean>, entity: WeatherBean.HeWeatherBean.DailyForecastBean) {
        super.convert(holder, entity)
        holder.itemView.findViewById<TextView>(R.id.forecast_date_text).text =
            entity.date
        holder.itemView.findViewById<TextView>(R.id.forecast_weather_text).text =
            entity.cond?.txt_d
        holder.itemView.findViewById<TextView>(R.id.forecast_max_tem_text).text=
            entity.tmp?.max
        holder.itemView.findViewById<TextView>(R.id.forecast_min_tem_text).text=
            entity.tmp?.min


    }
}
