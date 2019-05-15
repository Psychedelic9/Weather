package com.psychedelic.weather.base.ui

import android.view.MotionEvent
import android.widget.EditText
import androidx.databinding.ViewDataBinding
import cn.wj.android.common.expanding.hideSoftKeyboard
import cn.wj.android.common.tools.runOnMainThread
import cn.wj.android.common.tools.shouldHideInput
import cn.wj.android.common.ui.activity.CommonBaseBindingActivity
import com.psychedelic.weather.base.mvvm.BaseView
import com.psychedelic.weather.base.mvvm.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import com.psychedelic.weather.R

/**
 * Activity 基类
 */
abstract class BaseActivity<VM : BaseViewModel<V>, V : BaseView, DB : ViewDataBinding>
    : CommonBaseBindingActivity<VM, V, DB>(),
    BaseView {

    /** 标记 - 触摸输入框以外范围是否隐藏软键盘*/
    protected var touchToHideInput = true

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (touchToHideInput) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                if (shouldHideInput(v, ev)) {
                    // 需要隐藏软键盘
                    (v as? EditText)?.hideSoftKeyboard()
                }
                return super.dispatchTouchEvent(ev)
            }
            if (window.superDispatchTouchEvent(ev)) {
                return true
            }
            return onTouchEvent(ev)
        } else {
            return super.dispatchTouchEvent(ev)
        }
    }

    override fun startAnim() {
        overridePendingTransition(R.anim.app_anim_right_in, R.anim.app_anim_alpha_out)
    }

    override fun finishAnim() {
        overridePendingTransition(R.anim.app_anim_alpha_in, R.anim.app_anim_right_out)
    }

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
        finish()
    }
}