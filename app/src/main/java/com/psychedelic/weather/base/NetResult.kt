package com.psychedelic.weather.base

import android.util.Log
import com.orhanobut.logger.Logger
import com.psychedelic.weather.constents.NET_RESPONSE_CODE_SUCCESS

/**
 * 网络请求返回数据基类
 *
 * @param code 返回码
 * @param msg 接口信息
 * @param data 返回数据
 */
data class NetResult<E>(
        var code: Int = -1,
        var msg: String? = "",
        var data: E? = null
) {

    /**
     * 检查请求返回数据，判断请求是否成功
     *
     * @return 是否成功
     */
    fun success(): Boolean {
        Log.d("success","返回码 = "+code)
        when (code) {
            NET_RESPONSE_CODE_SUCCESS -> {
                Log.d("NetResult","请求成功！！！")

                // 请求成功
            }else ->{
            Log.d("NetResult","请求失败！！！")
        }
        }
        return code == NET_RESPONSE_CODE_SUCCESS
    }
}