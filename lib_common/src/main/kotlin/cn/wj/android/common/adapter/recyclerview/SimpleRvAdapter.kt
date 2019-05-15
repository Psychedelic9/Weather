package cn.wj.android.common.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.wj.android.common.BR

/**
 * 简易 列表适配器
 */
open class SimpleRvAdapter<E>(val layoutResId: Int, mLoadMoreEnable: Boolean = false) : BaseRvAdapter<
        SimpleRvAdapter.ViewHolder<E>,
        E>(mLoadMoreEnable) {

    var mModel: Any? = null

    override fun customCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder<E> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutResId, parent, false
        )
        binding.setVariable(BR.model, mModel)
        return ViewHolder(binding)
    }

    class ViewHolder<E> : BaseRvDBViewHolder<ViewDataBinding, E> {

        constructor(view: View) : super(view)

        constructor(binding: ViewDataBinding) : super(binding)
    }
}