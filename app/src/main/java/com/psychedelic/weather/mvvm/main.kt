package com.psychedelic.weather.mvvm

import android.util.Log
import androidx.databinding.BaseObservable
import cn.wj.android.common.utils.ToastUtil
import com.psychedelic.weather.base.mvvm.BaseView
import com.psychedelic.weather.base.mvvm.BaseViewModel
import com.psychedelic.weather.entity.ProvinceEntity
import com.psychedelic.weather.model.StoresItemModel
import com.psychedelic.weather.repository.DataRepository
import java.util.ArrayList


/**
 *@Author: yiqing
 *@CreateDate: 2019/5/9 14:31
 *@UpdateDate: 2019/5/9 14:31
 *@Description:
 *@ClassName: main
 */
class MainViewModel(private val repository: DataRepository) : BaseViewModel<MainView>() {
    val mModel = Model()
    private var firstLoad = true
    var provinceCode = ""
    override fun onCreate() {
        super.onCreate()
    }

        override fun onResume() {
        super.onResume()
        repository.getProvince({
        mView?.notifyList(it)
        },{})
    }

    override fun unsubscribe() {
        repository.unsubscribe()
    }
    inner class Model : BaseObservable(), StoresItemModel {
        var title = ""

        /** 设置点击 */
        val onSettingClick: () -> Unit = {
            ToastUtil.show("点什么点！")
        }

        /** 搜索点击 */
        val onSearchClick: () -> Unit = {

        }
        /** 省份点击 */
        override val onItemClick: (ProvinceEntity) -> Unit = {
            Log.d("onItemClick","点击了"+it.id)
            provinceCode = it.id
            mView?.startCitySelect(provinceCode)
            repository.getCity(it.id,{
                Log.d("onItemClick",it.toString())
            },{})

        }

    }
}

interface MainView : BaseView {
    fun notifyList(list: ArrayList<ProvinceEntity>)
    fun startCitySelect(provinceCode:String)
}