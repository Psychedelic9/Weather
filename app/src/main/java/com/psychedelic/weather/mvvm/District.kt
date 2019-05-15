package com.psychedelic.weather.mvvm

import android.util.Log
import androidx.databinding.BaseObservable
import cn.wj.android.common.utils.ToastUtil
import com.psychedelic.weather.base.mvvm.BaseView
import com.psychedelic.weather.base.mvvm.BaseViewModel
import com.psychedelic.weather.entity.CityEntity
import com.psychedelic.weather.entity.DistrictEntity
import com.psychedelic.weather.model.CitiesItemModel
import com.psychedelic.weather.model.DistrictsItemModel
import com.psychedelic.weather.repository.DataRepository
import java.util.ArrayList

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/13 17:11
 *@UpdateDate: 2019/5/13 17:11
 *@Description:
 *@ClassName: DistrictEntity
 */
class DistrictViewMode(private val repository: DataRepository) : BaseViewModel<DistrictView>() {
    val mModel = Model()

    private var mProvinceCode ="110000"
    private var mCityCode = "110100"
    override fun onResume() {
        super.onResume()
        mProvinceCode = mView!!.getProvinceCode()
        mCityCode = mView!!.getCityCode()
        repository.getDistrict(mProvinceCode,mCityCode,{
            mView?.notifyList(it)
        },{})

    }

    override fun unsubscribe() {
        repository.unsubscribe()
    }
    inner class Model : BaseObservable(), DistrictsItemModel {
        var title = ""
        var cityCode = ""

        /** 设置点击 */
        val onSettingClick: () -> Unit = {
            ToastUtil.show("点什么点！")
        }

        /** 搜索点击 */
        val onSearchClick: () -> Unit = {

        }
        /** 区域点击 */
        override val onItemClick: (DistrictEntity) -> Unit = {
            Log.d("onItemClick","点击了"+it.id)
            mView?.startWeatherActivity(mCityCode,it.id)
        }

    }
}

interface DistrictView : BaseView {
    fun notifyList(list: ArrayList<DistrictEntity>)
    fun getProvinceCode():String
    fun getCityCode():String
    fun startWeatherActivity(cityCode:String,districtCode:String)

}