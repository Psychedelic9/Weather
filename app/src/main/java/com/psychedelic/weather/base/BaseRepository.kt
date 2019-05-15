package com.psychedelic.weather.base

import com.psychedelic.weather.net.WebService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * 数据获取基类
 */
abstract class BaseRepository : KoinComponent {

    /** Web 接口 */
    protected val webService: WebService by inject()

    /** RxJava2 生命周期管理  */
    private var disposables: CompositeDisposable? = null

    init {
        // 初始化
        disposables = CompositeDisposable()
    }


    /**
     * 取消 Rx 订阅
     */
    fun unsubscribe() {
        disposables?.clear()
        disposables = null
    }

    /**
     * 将网络请求添加到 RxJava2 生命周期
     */
    fun addDisposable(dis: Disposable) {
        disposables?.add(dis)
    }

}