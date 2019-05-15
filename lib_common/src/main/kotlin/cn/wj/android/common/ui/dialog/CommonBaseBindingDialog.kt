package cn.wj.android.common.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.wj.android.common.mvvm.CommonBaseView
import cn.wj.android.common.mvvm.CommonBaseViewModel

/**
 * Dialog 弹窗基类
 *
 * @param VM MVVM ViewModel 类，继承 [CommonBaseViewModel]
 * @param V MVVM View 类，继承 [CommonBaseView]
 * @param DB [ViewDataBinding] 对象
 */
abstract class CommonBaseBindingDialog<VM : CommonBaseViewModel<V>, V : CommonBaseView, DB : ViewDataBinding>
    : CommonBaseMvvmDialog<VM, V>() {

    /** DataBinding 对象 */
    protected lateinit var mBinding: DB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // 取消标题栏
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0x00000000))

        // 初始化 DataBinding
        mBinding = DataBindingUtil.inflate(inflater, layoutResID, container, false)

        // 绑定声明周期
//        mBinding.setLifecycleOwner(this)

        // 绑定 ViewModel
//        mBinding.setVariable(BR.model, mViewModel)
//        mBinding.executePendingBindings()

        initView()

        // 初始化布局
        mRootView = mBinding.root

        return mRootView
    }
}