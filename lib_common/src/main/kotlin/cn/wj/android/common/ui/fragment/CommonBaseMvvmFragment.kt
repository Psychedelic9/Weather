package cn.wj.android.common.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import cn.wj.android.common.mvvm.CommonBaseView
import cn.wj.android.common.mvvm.CommonBaseViewModel

/**
 * Fragment基类
 *
 * @param VM MVVM ViewModel 类，继承 [CommonBaseViewModel]
 * @param V MVVM View 类，继承 [CommonBaseView]
 */
abstract class CommonBaseMvvmFragment<VM : CommonBaseViewModel<V>, V : CommonBaseView>
    : CommonBaseFragment(),
    CommonBaseView {

    /** 当前界面 ViewModel 对象 */
    abstract val mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 绑定 MVVM
        @Suppress("UNCHECKED_CAST")
        mViewModel.attach(this as? V)
        // 添加生命周期监听
        lifecycle.addObserver(mViewModel)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        // 移除生命周期监听
        lifecycle.removeObserver(mViewModel)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mViewModel.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }
}