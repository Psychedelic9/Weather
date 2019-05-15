package com.psychedelic.weather.mvvm

import android.util.Log
import androidx.databinding.BaseObservable
import cn.wj.android.common.utils.ToastUtil
import com.psychedelic.weather.base.mvvm.BaseView
import com.psychedelic.weather.base.mvvm.BaseViewModel
import com.psychedelic.weather.entity.CityEntity
import com.psychedelic.weather.model.CitiesItemModel
import com.psychedelic.weather.repository.DataRepository
import java.util.ArrayList

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/13 15:39
 *@UpdateDate: 2019/5/13 15:39
 *@Description:
 *@ClassName: CityEntity
 */
class CityViewMode(private val repository: DataRepository) : BaseViewModel<CityView>() {
    val mModel = Model()
    private var firstLoad = true
    override fun onCreate() {
        super.onCreate()
    }

    private var mProvinceCode ="110000"
    private var mCityCode = "110100"

    override fun onResume() {
        super.onResume()
        mProvinceCode = mView!!.getProvinceCode()
        repository.getCity(mProvinceCode,{
            mView?.notifyList(it)
        },{})

    }

    override fun unsubscribe() {
        repository.unsubscribe()
    }
    inner class Model : BaseObservable(), CitiesItemModel {
        var title = ""
        var cityCode = ""

        /** 设置点击 */
        val onSettingClick: () -> Unit = {
            ToastUtil.show("点什么点！")
        }

        /** 搜索点击 */
        val onSearchClick: () -> Unit = {

        }
        /** 省份点击 */
        override val onItemClick: (CityEntity) -> Unit = {
            Log.d("onItemClick","点击了"+it.id)
            mCityCode = it.id
            mView!!.startDistrictSelect(mProvinceCode,mCityCode)
        }

    }
}

interface CityView : BaseView {
    fun notifyList(list: ArrayList<CityEntity>)
    fun getProvinceCode():String
    fun startDistrictSelect(provinceCode:String,cityCode:String)
}