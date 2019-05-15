package com.psychedelic.weather.base.mvvm

import cn.wj.android.common.mvvm.CommonBaseView
import cn.wj.android.common.tools.getString
import com.google.android.material.snackbar.Snackbar

/**
 * MVVM View 基类
 */
interface BaseView : CommonBaseView {

    /**
     * 显示提示
     *
     * @param str 提示文本
     * @param duration 显示时间
     * @param onDismiss 隐藏回调
     */
    fun showTips(str: String?, duration: Int = Snackbar.LENGTH_SHORT, onDismiss: ((Int) -> Unit)? = null)

    /**
     * 显示提示
     *
     * @param strResID 提示文本
     * @param duration 显示时间
     * @param onDismiss 隐藏回调
     */
    fun showTips(strResID: Int, duration: Int = Snackbar.LENGTH_SHORT, onDismiss: ((Int) -> Unit)? = null) {
        showTips(strResID.getString(), duration, onDismiss)
    }

    /**
     * 显示错误
     *
     * @param str 提示文本
     * @param duration 显示时间
     * @param onDismiss 隐藏回调
     */
    fun showError(str: String?, duration: Int = Snackbar.LENGTH_SHORT, onDismiss: ((Int) -> Unit)? = null)

    /**
     * 显示错误
     *
     * @param strResID 提示文本
     * @param duration 显示时间
     * @param onDismiss 隐藏回调
     */
    fun showError(strResID: Int, duration: Int = Snackbar.LENGTH_SHORT, onDismiss: ((Int) -> Unit)? = null) {
        showError(strResID.getString(), duration, onDismiss)
    }

//    /**
//     * 显示进度条弹窗
//     *
//     * @param cancelable 能否取消 默认 false
//     */
//    fun showProgressDialog(cancelable: Boolean = false)
//
//    /**
//     * 隐藏进度条弹窗
//     */
//    fun dismissProgressDialog()

    /**
     * 关闭当前视图
     */
    fun closeInterf()
}