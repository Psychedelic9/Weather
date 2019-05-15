package com.psychedelic.weather.utils

import androidx.annotation.StringRes
import cn.wj.android.common.tools.toast
import cn.wj.android.common.utils.AppManager
import com.psychedelic.weather.base.mvvm.BaseView
import com.google.android.material.snackbar.Snackbar

/**
 * Snackbar 工具类
 */
object SnackbarUtil {

    /**
     * 弹出 Snackbar
     *
     * @param str 字符串
     */
    fun showTips(str: String, duration: Int = Snackbar.LENGTH_SHORT, onDismiss: ((Int) -> Unit)? = null) {
        val view = AppManager.getFocusView() as? BaseView
        if (view != null) {
            view.showTips(str, duration, onDismiss)
        } else {
            toast(str)
        }
    }

    /**
     * 弹出 Snackbar
     *
     * @param strResID 字符串资源 id
     */
    fun showTips(@StringRes strResID: Int, duration: Int = Snackbar.LENGTH_SHORT, onDismiss: ((Int) -> Unit)? = null) {
        val view = AppManager.getFocusView() as? BaseView
        if (view != null) {
            view.showTips(strResID, duration, onDismiss)
        } else {
            toast(strResID)
        }
    }

    /**
     * 弹出 Snackbar
     *
     * @param str 字符串
     */
    fun showError(str: String, duration: Int = Snackbar.LENGTH_SHORT, onDismiss: ((Int) -> Unit)? = null) {
        val view = AppManager.getFocusView() as? BaseView
        if (view != null) {
            view.showError(str, duration, onDismiss)
        } else {
            toast(str)
        }
    }

    /**
     * 弹出 Snackbar
     *
     * @param strResID 字符串资源 id
     */
    fun showError(@StringRes strResID: Int, duration: Int = Snackbar.LENGTH_SHORT, onDismiss: ((Int) -> Unit)? = null) {
        val view = AppManager.getFocusView() as? BaseView
        if (view != null) {
            view.showError(strResID, duration, onDismiss)
        } else {
            toast(strResID)
        }
    }
}