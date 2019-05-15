package cn.wj.android.common.ui.activity

import android.os.Bundle
import android.view.MenuItem
import cn.wj.android.common.mvvm.CommonBaseView
import cn.wj.android.common.mvvm.CommonBaseViewModel

/**
 * Activity 基类
 * - MVVM 模式
 *
 * @param VM MVVM ViewModel 类，继承 [CommonBaseViewModel]
 * @param V MVVM View 类，继承 [CommonBaseView]
 */
abstract class CommonBaseMvvmActivity<VM : CommonBaseViewModel<V>, V : CommonBaseView>
    : CommonBaseActivity(),
    CommonBaseView {

    /** 当前界面 ViewModel 对象 */
    abstract val mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 初始化数据
        initBefore()

        // 绑定 MVVM
        @Suppress("UNCHECKED_CAST")
        mViewModel.attach(this as? V)
        // 添加生命周期监听
        lifecycle.addObserver(mViewModel)
    }

    override fun onDestroy() {

        super.onDestroy()

        // 移除生命周期监听
        lifecycle.removeObserver(mViewModel)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item == null) {
            super.onOptionsItemSelected(item)
        } else {
            mViewModel.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
        }
    }
}