package cn.wj.android.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.wj.android.common.mvvm.CommonBaseView
import cn.wj.android.common.mvvm.CommonBaseViewModel

/**
 * Fragment基类
 *
 * @param VM MVVM ViewModel 类，继承 [CommonBaseViewModel]
 * @param V MVVM View 类，继承 [CommonBaseView]
 * @param DB [ViewDataBinding] 对象
 */
abstract class CommonBaseBindingFragment<VM : CommonBaseViewModel<V>, V : CommonBaseView, DB : ViewDataBinding>
    : CommonBaseMvvmFragment<VM, V>() {

    /** DataBinding 对象 */
    protected lateinit var mBinding: DB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // 初始化 DataBinding
        mBinding = DataBindingUtil.inflate(inflater, layoutResID, container, false)

        // 绑定声明周期
//        mBinding.setLifecycleOwner(this)

        // 绑定 ViewModel
//        mBinding.setVariable(BR.model, mViewModel)
//        mBinding.executePendingBindings()

        mRootView = mBinding.root

        // 初始化布局
        initView()

        return mRootView
    }
}