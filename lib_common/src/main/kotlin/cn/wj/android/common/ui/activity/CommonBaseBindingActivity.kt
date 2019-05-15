package cn.wj.android.common.ui.activity

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.wj.android.common.mvvm.CommonBaseView
import cn.wj.android.common.mvvm.CommonBaseViewModel

/**
 * Activity 基类
 *
 * @param VM MVVM ViewModel 类，继承 [CommonBaseViewModel]
 * @param V MVVM View 类，继承 [CommonBaseView]
 * @param DB [ViewDataBinding] 对象
 */
abstract class CommonBaseBindingActivity<VM : CommonBaseViewModel<V>, V : CommonBaseView, DB : ViewDataBinding>
    : CommonBaseMvvmActivity<VM, V>() {

    /** DataBinding 对象 */
    protected lateinit var mBinding: DB

    override fun setContentView(layoutResID: Int) {
        // 初始化 DataBinding
        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            layoutResID, null, false
        )

        // 绑定声明周期
//        mBinding.setLifecycleOwner(this)

        // 绑定 ViewModel
//        mBinding.setVariable(BR.model, mViewModel)
//        mBinding.executePendingBindings()

        // 设置布局
        super.setContentView(mBinding.root)
    }
}