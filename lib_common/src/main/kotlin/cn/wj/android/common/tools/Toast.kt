@file:Suppress("unused")

package cn.wj.android.common.tools

import androidx.annotation.StringRes
import cn.wj.android.common.utils.ToastUtil

/* ----------------------------------------------------------------------------------------- */
/* |                                       Toast相关                                        | */
/* ----------------------------------------------------------------------------------------- */

/**
 * 弹 Toast
 *
 * @param str 显示文本
 */
fun toast(str: String?) {
    ToastUtil.show(str)
}

/**
 * 弹 Toast
 *
 * @param resId 显示文本资源 id
 */
fun toast(@StringRes resId: Int) {
    ToastUtil.show(resId)
}