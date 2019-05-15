package cn.wj.android.common.ui.dialog

import android.os.Bundle
import android.view.MenuItem
import cn.wj.android.common.mvvm.CommonBaseView
import cn.wj.android.common.mvvm.CommonBaseViewModel
import cn.wj.android.common.utils.AppManager

/**
 * Dialog 弹窗基类
 *
 * @param VM MVVM ViewModel 类，继承 [CommonBaseViewModel]
 * @param V MVVM View 类，继承 [CommonBaseView]
 */
abstract class CommonBaseMvvmDialog<VM : CommonBaseViewModel<V>, V : CommonBaseView>
    : CommonBaseDialog(),
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        AppManager.mDialogLifecycleCallbacks.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        AppManager.mDialogLifecycleCallbacks.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        mViewModel.unsubscribe()
        // 移除生命周期监听
        lifecycle.removeObserver(mViewModel)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mViewModel.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }
}