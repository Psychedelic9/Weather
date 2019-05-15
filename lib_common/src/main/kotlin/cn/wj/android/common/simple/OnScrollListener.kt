package cn.wj.android.common.simple

import androidx.recyclerview.widget.RecyclerView

/**
 * 设置RecyclerView 滑动监听
 */
fun RecyclerView.addOnScrollListener(init: SimpleOnScrollListener.() -> Unit) {
    val listener = SimpleOnScrollListener()
    listener.init()
    this.addOnScrollListener(listener)
}

open class SimpleOnScrollListener : RecyclerView.OnScrollListener() {

    private var onScrollStateChanged: ((RecyclerView, Int) -> Unit)? = null

    fun onScrollStateChanged(onScrollStateChanged: (RecyclerView, Int) -> Unit) {
        this.onScrollStateChanged = onScrollStateChanged
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        onScrollStateChanged?.invoke(recyclerView, newState)
    }

    private var onScrolled: ((RecyclerView, Int, Int) -> Unit)? = null

    fun onScrolled(onScrolled: (RecyclerView, Int, Int) -> Unit) {
        this.onScrolled = onScrolled
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        onScrolled?.invoke(recyclerView, dx, dy)
    }
}