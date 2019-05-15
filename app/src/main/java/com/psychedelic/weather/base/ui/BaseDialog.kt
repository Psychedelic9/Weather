package com.arcb.laborunion.base.ui

import androidx.databinding.ViewDataBinding
import cn.wj.android.common.tools.runOnMainThread
import cn.wj.android.common.ui.dialog.CommonBaseBindingDialog
import com.psychedelic.weather.base.mvvm.BaseView
import com.psychedelic.weather.base.mvvm.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import com.psychedelic.weather.R

/**
 * Dialog 基类
 */
abstract class BaseDialog<VM : BaseViewModel<V>, V : BaseView, DB : ViewDataBinding>
    : CommonBaseBindingDialog<VM, V, DB>(),
    BaseView {

    override fun showTips(str: String?, duration: Int, onDismiss: ((Int) -> Unit)?) {
        runOnMainThread {
            Snackbar.make(mBinding.root, str.orEmpty(), duration).apply {
                view.setBackgroundResource(R.color.app_snackbar_tips)
                addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        onDismiss?.invoke(event)
                    }
                })
            }.show()
        }
    }

    override fun showError(str: String?, duration: Int, onDismiss: ((Int) -> Unit)?) {
        runOnMainThread {
            Snackbar.make(mBinding.root, str.orEmpty(), duration).apply {
                view.setBackgroundResource(R.color.app_snackbar_error)
                addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        onDismiss?.invoke(event)
                    }
                })
            }.show()
        }
    }

    override fun closeInterf() {
        mContext.finish()
    }
}