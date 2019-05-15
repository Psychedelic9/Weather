@file:Suppress("unused")

package cn.wj.android.common.tools

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/* ----------------------------------------------------------------------------------------- */
/* |                                         Rx 相关                                        | */
/* ----------------------------------------------------------------------------------------- */

/**
 * 异步线程
 * - 请求在 io 线程
 * - 回调在 主线程
 */
fun <T> Observable<T>.asyncSchedulers(): Observable<T> =
    subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

/**
 * 在主线程运行
 *
 * @param run 运行内容
 */
fun runOnMainThread(run: () -> Unit): Disposable =
    Observable.just(0)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            run.invoke()
        }, { throwable ->
            Log.e("Common_Rx", throwable.localizedMessage)
        })

/**
 * 在主线程运行
 *
 * @param run 运行内容
 * @param error 异常回调
 */
fun runOnMainThread(run: () -> Unit, error: (Throwable) -> Unit): Disposable =
    Observable.just(0)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            run.invoke()
        }, { throwable ->
            Log.e("Common_Rx", throwable.localizedMessage)
            error.invoke(throwable)
        })

/**
 * 在 IO 线程运行
 *
 * @param run 运行内容
 */
fun runOnIOThread(run: () -> Unit): Disposable =
    Observable.just(0)
        .observeOn(Schedulers.io())
        .subscribe({
            run.invoke()
        }, { throwable ->
            Log.e("Common_Rx", throwable.localizedMessage)
        })

/**
 * 在 IO 线程运行
 *
 * @param run 运行内容
 * @param error 异常回调
 */
fun runOnIOThread(run: () -> Unit, error: (Throwable) -> Unit): Disposable =
    Observable.just(0)
        .observeOn(Schedulers.io())
        .subscribe({
            run.invoke()
        }, { throwable ->
            Log.e("Common_Rx", throwable.localizedMessage)
            error.invoke(throwable)
        })

/**
 * 异步任务
 *
 * @param subscribe IO 线程运行内容
 * @param consumer 主线程回调运行内容
 * @param error 异常回调 可为空
 */
fun <T> runAsync(subscribe: () -> T, consumer: (T) -> Unit, error: ((Throwable) -> Unit)? = null) =
    Observable.create<T> { oe ->
        oe.onNext(subscribe.invoke())
        oe.onComplete()
    }.asyncSchedulers()
        .subscribe({ result ->
            consumer.invoke(result)
        }, { throwable ->
            Log.e("Common_Rx", throwable.localizedMessage)
            error?.invoke(throwable)
        })!!

/**
 * 延时操作
 *
 * @param time 延时时间
 * @param unit 延时单位
 * @param sub 延时后事件
 */
fun delay(time: Long, unit: TimeUnit, sub: () -> Unit): Disposable =
    Observable.timer(time, unit)
        .asyncSchedulers()
        .subscribe { sub.invoke() }


/**
 * 延时操作
 *
 * @param time 延时时间 单位：毫秒
 * @param sub 延时后事件
 */
fun millisecondsTimeDelay(time: Long, sub: () -> Unit) = delay(time, TimeUnit.MILLISECONDS, sub)

/**
 * 延时操作
 *
 * @param time 延时时间 单位：秒
 * @param sub 延时后事件
 */
fun secondsTimeDelay(time: Long, sub: () -> Unit) = delay(time, TimeUnit.SECONDS, sub)