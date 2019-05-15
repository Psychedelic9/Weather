package com.psychedelic.weather.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cn.wj.android.common.utils.AppManager
import com.orhanobut.logger.Logger
import com.psychedelic.weather.base.ui.BaseActivity
import com.psychedelic.weather.R
import com.psychedelic.weather.adapter.AreaSelectAdapter
import com.psychedelic.weather.databinding.ActivityMainBinding
import com.psychedelic.weather.entity.ProvinceEntity
import com.psychedelic.weather.mvvm.MainView
import com.psychedelic.weather.mvvm.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.ArrayList

class MainActivity
    : BaseActivity<MainViewModel, MainView, ActivityMainBinding>(), MainView {


    companion object {

        /**
         * 界面入口
         *
         * @param context Context 对象
         */
        fun actionStart(context: Context) {
            if (AppManager.contains(MainActivity::class.java)) {
                return
            }
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override val mViewModel: MainViewModel by viewModel()
    private val mAdapter:AreaSelectAdapter by inject()
    val instance by lazy { this }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("onCreate")
        setContentView(R.layout.activity_main)
        // 绑定 Model
        mBinding.model = mViewModel.mModel
        mAdapter.mModel= mViewModel.mModel
        mBinding.chooseAreaRecycleView.layoutManager = LinearLayoutManager(this)
        mBinding.chooseAreaRecycleView.adapter = mAdapter
    }
    override fun notifyList(list: ArrayList<ProvinceEntity>) {
        // 刷新商户列表
        mAdapter.refresh(list)
    }
    override fun startCitySelect(provinceCode: String) {
        intent = Intent(instance,ChooseCityActivity::class.java)
        intent.putExtra("provinceCode",provinceCode)
        instance.startActivity(intent)
    }
}
