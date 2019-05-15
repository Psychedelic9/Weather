package com.psychedelic.weather.base.mvvm

import android.view.MenuItem
import cn.wj.android.common.mvvm.CommonBaseViewModel
import org.koin.core.KoinComponent

/**
 * MVVM ViewModel 基类
 */
abstract class BaseViewModel<V : BaseView>
    : CommonBaseViewModel<V>(),
    KoinComponent {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            mView?.closeInterf()
            true
        } else {
            false
        }
    }
}