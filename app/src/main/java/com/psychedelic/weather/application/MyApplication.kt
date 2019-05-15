package com.psychedelic.weather.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import cn.wj.android.common.utils.AppManager
import com.didichuxing.doraemonkit.DoraemonKit
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.psychedelic.weather.BuildConfig
import com.psychedelic.weather.constents.LOGGER_TAG
import com.psychedelic.weather.koin.adapterModule
import com.psychedelic.weather.koin.netModule
import com.psychedelic.weather.koin.repositoryModule
import com.psychedelic.weather.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/9 10:44
 *@UpdateDate: 2019/5/9 10:44
 *@Description:
 *@ClassName: MyApplication
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
DoraemonKit.install(this)
AppManager.register(this)
        // 初始化 Logger
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .methodCount(5) // 显示方法行数
            .logStrategy(object : LogStrategy {
                override fun log(priority: Int, tag: String?, message: String) {
                    val randomKey = randomKey()
                    Log.println(priority, randomKey + tag, message)
                }

                private var last = 0

                private fun randomKey(): String {
                    var random = (10 * Math.random()).toInt()
                    if (random == last) {
                        random = (random + 1) % 10
                    }
                    last = random
                    return random.toString()
                }
            })
            .tag(LOGGER_TAG) // 全局标记
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
        })
        // 初始化 Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(netModule, viewModelModule, repositoryModule, adapterModule)
        }

        //TODO:64k分包
//        override fun attachBaseContext(base: Context?) {
//            super.attachBaseContext(base)
//            // 解决 64k 分包
//            MultiDex.install(this)
//
//            fixTimeout()
//        }

    }
    /**
     * 处理 Timeout 异常
     */
    private fun fixTimeout() {
        try {
            val c = Class.forName("java.lang.Daemons")
            val maxField = c.getDeclaredField("MAX_FINALIZE_NANOS")
            maxField.isAccessible = true
            maxField.set(null, Long.MAX_VALUE)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        try {
            val clazz = Class.forName("java.lang.Daemons\$FinalizerWatchdogDaemon")
            val method = clazz.superclass?.getDeclaredMethod("stop")
            method?.isAccessible = true
            val field = clazz.getDeclaredField("INSTANCE")
            field.isAccessible = true
            method?.invoke(field.get(null))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}