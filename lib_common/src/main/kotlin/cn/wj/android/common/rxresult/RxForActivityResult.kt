package cn.wj.android.common.rxresult

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.reactivex.Observable

/**
 * 处理 startActivityForResult
 */
@Suppress("unused")
class RxForActivityResult(activity: AppCompatActivity) {

    private val rxForActivityResultFragment: RxForActivityResultFragment

    init {
        rxForActivityResultFragment = getRxResultFragment(activity)
    }

    /** 目标界面 */
    private var target: Class<out Activity>? = null
    /** 请求码 */
    private var requestCode = -1
    /** 携带数据 */
    private val bundle = Bundle()

    private fun getRxResultFragment(activity: AppCompatActivity): RxForActivityResultFragment {
        var rxForActivityResultFragment: RxForActivityResultFragment? = findRxResultFragment(activity)
        val isNewInstance = rxForActivityResultFragment == null
        if (isNewInstance) {
            rxForActivityResultFragment = RxForActivityResultFragment()
            val fragmentManager = activity.supportFragmentManager
            fragmentManager
                    .beginTransaction()
                    .add(rxForActivityResultFragment, "RxForActivityResult")
                    .commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }
        return rxForActivityResultFragment!!
    }

    private fun findRxResultFragment(activity: AppCompatActivity): RxForActivityResultFragment? {
        return activity.supportFragmentManager.findFragmentByTag("RxForActivityResult") as? RxForActivityResultFragment
    }

    fun requestCode(code: Int): RxForActivityResult {
        this.requestCode = code
        return this
    }

    fun target(clazz: Class<out Activity>): RxForActivityResult {
        this.target = clazz
        return this
    }

    fun params(params: Bundle): RxForActivityResult {
        this.bundle.putAll(params)
        return this
    }

    fun startForResult(): Observable<RxForActivityResultInfo> {
        if (requestCode == -1) {
            throw IllegalArgumentException("Please set requestCode first!")
        }
        if (target == null) {
            throw NullPointerException("Target Activity could not be null!")
        }
        val intent = Intent(rxForActivityResultFragment.activity, target).apply {
            putExtras(bundle)
        }
        return rxForActivityResultFragment.startForResult(requestCode, intent)
    }

    fun startForResult(start: (Fragment) -> Unit): Observable<RxForActivityResultInfo> {
        if (requestCode == -1) {
            throw IllegalArgumentException("Please set requestCode first!")
        }
        return rxForActivityResultFragment.startForResult(requestCode, start)
    }
}

fun Observable<RxForActivityResultInfo>.withSuccess(): Observable<RxForActivityResultInfo> {
    return this.filter { result -> result.resultCode == Activity.RESULT_OK }
}

fun Observable<RxForActivityResultInfo>.withData(): Observable<Intent> {
    return this.filter { result -> result.resultCode == Activity.RESULT_OK && result.result != null }
            .map { it.result!! }
}

