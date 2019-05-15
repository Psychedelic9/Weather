package com.psychedelic.weather.expanding

import android.util.Log
import com.orhanobut.logger.Logger
import com.psychedelic.weather.R
import com.psychedelic.weather.utils.SnackbarUtil
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * 处理网络请求回调
 */
fun <T> Observable<T>.netSubscribe(result: (T) -> Unit, throwable: ((Throwable) -> Unit)? = null): Disposable {
    return this
            .subscribe({
                result(it)
                Log.d("Net","result = "+result.toString()+it.toString())
            }, {
                it.dispose()
                throwable?.invoke(it)
            })
}

fun Throwable.dispose() {
    // 打印日志
    Log.e("Net", "NET_ERROR")
    when (this) {
        is SocketTimeoutException -> {
            // 连接超时
            SnackbarUtil.showError(R.string.app_net_socket_time_out)
        }
        is ConnectException -> {
            // 网络连接问题
            SnackbarUtil.showError(R.string.app_net_not_connected)
        }
        else -> {
            // 网络故障
            SnackbarUtil.showError(R.string.app_net_error)
            Log.d("Net","Exception = "+this.printStackTrace())
        }
    }
}