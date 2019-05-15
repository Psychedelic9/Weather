package cn.wj.android.common.mvvm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

/**
 * MVVM ViewModel 基类
 * - 继承 [ViewModel]
 * - 实现 [LifecycleObserver] 监听生命周期
 */
abstract class CommonBaseViewModel<V : CommonBaseView>
    : ViewModel(),
    LifecycleObserver {

    /** View 对象  */
    protected var mView: V? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
        Log.d("Common_MVVM", "View onCreate ---->> $mView")
    }

    /**
     * 仅 Fragment 使用
     */
    open fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("Common_MVVM", "View onActivityCreated ---->> $mView")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        Log.d("Common_MVVM", "View onStart ---->> $mView")
    }

    /**
     * 从 Activity 返回且又返回数据
     */
    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("Common_MVVM", "View onActivityResult ---->> $mView")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        Log.d("Common_MVVM", "View onResume ---->> $mView")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        Log.d("Common_MVVM", "View onPause ---->> $mView")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        Log.d("Common_MVVM", "View onStop ---->> $mView")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        Log.d("Common_MVVM", "View onDestroy ---->> $mView")
        // 解除绑定，移除依赖
        detach()
    }

    open fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }

    override fun onCleared() {
        super.onCleared()
        // 取消订阅
        unsubscribe()
    }

    /**
     * 界面绑定，关联 View
     *
     * @param view View
     */
    fun attach(view: V?) {
        mView = view
    }

    /**
     * 取消订阅
     */
    open fun unsubscribe() {}

    /**
     * 解除绑定，去除 View 引用
     */
    private fun detach() {
        mView = null
    }
}