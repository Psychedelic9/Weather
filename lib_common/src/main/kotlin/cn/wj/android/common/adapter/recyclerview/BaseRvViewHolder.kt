package cn.wj.android.common.adapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder基类
 *
 * @param E 数据实体类，与 Adapter 一致
 */
open class BaseRvViewHolder<E>(view: View) : RecyclerView.ViewHolder(view) {

    /**
     * 绑定数据
     *
     * @param entity 数据实体对象
     */
    open fun bindData(entity: E) {}
}