@file:Suppress("unused")

package cn.wj.android.common.tools

import java.util.*

/* ----------------------------------------------------------------------------------------- */
/* |                                        集合相关                                        | */
/* ----------------------------------------------------------------------------------------- */

/**
 * 交换位置
 */
fun List<*>.swap(fromPosition: Int, toPosition: Int) {
    Collections.swap(this, fromPosition, toPosition)
}