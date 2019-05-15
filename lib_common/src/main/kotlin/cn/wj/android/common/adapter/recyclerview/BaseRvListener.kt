package cn.wj.android.common.adapter.recyclerview

/**
 * 加载更多回调接口
 */
interface OnLoadMoreListener {
    /**
     * 加载更多
     */
    fun onLoadMore()
}

/**
 * 加载更多失败事件监听
 */
interface OnLoadMoreFailedClickListener {
    /**
     * 点击事件处理
     */
    fun onClick()
}

/**
 * 没有更多数据事件监听
 */
interface OnLoadMoreEndClickListener {
    /**
     * 点击事件处理
     */
    fun onClick()
}





