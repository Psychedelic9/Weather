package com.psychedelic.weather.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.psychedelic.weather.R
import com.psychedelic.weather.adapter.DistrictSelectAdapter
import com.psychedelic.weather.base.ui.BaseActivity
import com.psychedelic.weather.entity.DistrictEntity
import com.psychedelic.weather.mvvm.DistrictView
import com.psychedelic.weather.mvvm.DistrictViewMode
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.ArrayList

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/13 17:25
 *@UpdateDate: 2019/5/13 17:25
 *@Description:
 *@ClassName: ChooseDistrictActivity
 */
class ChooseDistrictActivity : BaseActivity<DistrictViewMode, DistrictView, com.psychedelic.weather.databinding.ActivityChooseDistrictBinding>(), DistrictView {


    override val mViewModel: DistrictViewMode by viewModel()
    private val mAdapter: DistrictSelectAdapter by inject()
    val instance by lazy { this }

    private var mProvinceCode = "110000"//北京
    private var mCityCode = "110100"//北京
    private var mCityName = "北京市"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_district)
        mProvinceCode = this.intent.getStringExtra("provinceCode")
        mCityCode = this.intent.getStringExtra("cityCode")
        mCityName = this.intent.getStringExtra("cityName")
        mBinding.model = mViewModel.mModel
        mAdapter.mModel = mViewModel.mModel
        mBinding.chooseDistrictRecycleView.layoutManager = LinearLayoutManager(this)
        mBinding.chooseDistrictRecycleView.adapter = mAdapter
    }

    override fun notifyList(list: ArrayList<DistrictEntity>) {
        mAdapter.refresh(list)
    }

    override fun getProvinceCode(): String {
        return mProvinceCode
    }
    override fun getCityCode(): String {
        return mCityCode
    }
    override fun startWeatherActivity(cityCode: String, districtCode: String) {
        intent = Intent(instance,WeatherActivity::class.java)
        intent.putExtra("CityCode",cityCode)
        intent.putExtra("DistrictCode",districtCode)
        intent.putExtra("CityName",mCityName)
        startActivity(intent)
   }
}
