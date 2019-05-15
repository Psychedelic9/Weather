package cn.wj.android.common.rxresult

import android.content.Intent

/**
 * Activity 返回数据实体类
 */
data class RxForActivityResultInfo(
    val requestCode: Int,
    val resultCode: Int,
    val result: Intent?
)