package com.psychedelic.weather.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.recyclerview.widget.LinearLayoutManager
import cn.wj.android.common.utils.ToastUtil
import com.psychedelic.weather.R
import com.psychedelic.weather.adapter.CitySelectAdapter
import com.psychedelic.weather.base.ui.BaseActivity
import com.psychedelic.weather.databinding.ActivityChooseCityBinding
import com.psychedelic.weather.entity.CityEntity
import com.psychedelic.weather.entity.ProvinceEntity
import com.psychedelic.weather.model.CitiesItemModel
import com.psychedelic.weather.model.StoresItemModel
import com.psychedelic.weather.mvvm.CityView
import com.psychedelic.weather.mvvm.CityViewMode
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.ArrayList

class ChooseCityActivity : BaseActivity<CityViewMode,CityView, ActivityChooseCityBinding>(),CityView {


    override val mViewModel: CityViewMode by viewModel()
    private val mAdapter: CitySelectAdapter by inject()
    private var mProvinceCode = "110000"//北京
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_city)
        mProvinceCode = this.intent.getStringExtra("provinceCode")

        mBinding.model = mViewModel.mModel
        mAdapter.mModel= mViewModel.mModel
        mBinding.chooseCityRecycleView.layoutManager = LinearLayoutManager(this)
        mBinding.chooseCityRecycleView.adapter = mAdapter
    }
    val instance by lazy { this }

    override fun notifyList(list: ArrayList<CityEntity>) {
        mAdapter.refresh(list)
    }
    override fun getProvinceCode(): String {
        return mProvinceCode
    }

    override fun startDistrictSelect(provinceCode: String, cityCode: String) {
        intent = Intent(instance,ChooseDistrictActivity::class.java)
        intent.putExtra("provinceCode",provinceCode)
        intent.putExtra("cityCode",cityCode)
        instance.startActivity(intent)
    }
}
