package com.psychedelic.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cn.wj.android.common.BR
import cn.wj.android.common.adapter.recyclerview.BaseRvAdapter
import cn.wj.android.common.adapter.recyclerview.BaseRvDBViewHolder
import cn.wj.android.common.adapter.recyclerview.BaseRvViewHolder


/**
 *@Author: yiqing
 *@CreateDate: 2019/5/10 13:57
 *@UpdateDate: 2019/5/10 13:57
 *@Description:
 *@ClassName: SimpleRvAdapter
 */
open class SimpleRvAdapter<E>(val layoutResId: Int, mLoadModeEnable: Boolean = false) :
    BaseRvAdapter<SimpleRvAdapter.ViewHolder<E>, E>(mLoadModeEnable) {
    var mModel: Any? = null

    override fun customCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvViewHolder<E> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutResId, parent, false
        )
        binding.setVariable(BR.model, mModel)
        return SimpleRvAdapter.ViewHolder(binding)
    }



    class ViewHolder<E> : BaseRvDBViewHolder<ViewDataBinding, E> {
        constructor(view: View) : super(view)
        constructor(binding: ViewDataBinding) : super(binding)
    }
}