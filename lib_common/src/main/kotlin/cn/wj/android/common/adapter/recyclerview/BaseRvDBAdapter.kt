package cn.wj.android.common.adapter.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.wj.android.common.BR
import java.lang.reflect.ParameterizedType

/**
 * RecyclerView 适配器基类
 *
 * @param VH ViewHolder 类型，继承 [BaseRvDBViewHolder]
 * @param DB  DataBinding 类型，与 VH 一致 继承 [ViewDataBinding]
 * @param VM  事件处理类型 ViewModel
 * @param E  数据类型
 *
 * @param mLoadMoreEnable 标记 - 是否允许加载更多 默认不允许
 */
abstract class BaseRvDBAdapter<out VH : BaseRvDBViewHolder<DB, E>, DB : ViewDataBinding, VM, E>(var mLoadMoreEnable: Boolean = false) :
    RecyclerView.Adapter<BaseRvDBViewHolder<DB, E>>() {

    companion object {
        /** 布局类型 - 头布局 */
        protected const val VIEW_TYPE_HEADER = 0x0101551
        /** 布局类型 - 正常 */
        protected const val VIEW_TYPE_NORMAL = 0x1011552
        /** 布局类型 - 脚布局 */
        protected const val VIEW_TYPE_FOOTER = 0x0101553
        /** 布局类型 - 空布局 */
        protected const val VIEW_TYPE_EMPTY = 0x0101554
        /** 布局类型 - 加载更多 */
        protected const val VIEW_TYPE_LOADING = 0x0101555
    }

    /** Adapter 绑定的 RecyclerView 对象 */
    var mRecyclerView: RecyclerView? = null

    /** 数据集合  */
    val mData = arrayListOf<E>()

    /** 事件处理  */
    var mViewModel: VM? = null

    /** 空布局 */
    private var mEmptyLayout: FrameLayout? = null
    /** 头布局 */
    private var mHeaderLayout: LinearLayout? = null
    /** 脚布局 */
    private var mFooterLayout: LinearLayout? = null

    /** 空布局布局 id */
    private var emptyViewLayoutResID: Int? = null

    /** 标记 - 显示空布局是是否显示头布局 默认不显示 */
    var showHeaderWhenEmpty = false
    /** 标记 - 显示空布局时是否显示脚布局 默认不显示 */
    var showFooterWhenEmpty = false

    /** 加载更多布局 */
    private var mLoadMoreView: BaseRvLoadMoreView? = SimpleLoadMoreView()

    /**
     * 根据 View 下标获取当前 View 布局类型
     *
     * @param position View 下标
     * @return View 类型 [VIEW_TYPE_HEADER]、[VIEW_TYPE_NORMAL]、[VIEW_TYPE_FOOTER]
     */
    override fun getItemViewType(position: Int) = when {
        isLoading(position) -> VIEW_TYPE_LOADING // 加载更多
        isEmpty(position) -> VIEW_TYPE_EMPTY     // 空布局
        isHeader(position) -> VIEW_TYPE_HEADER   // 头布局
        isFooter(position) -> VIEW_TYPE_FOOTER   // 脚布局
        else -> customViewType(if (haveHeader()) position - 1 else position) // 其他布局
    }

    /**
     * 除特殊布局外的其他布局，一种布局无需变化，多种布局重写
     */
    open fun customViewType(position: Int): Int {
        return VIEW_TYPE_NORMAL
    }

    override fun getItemCount() = if (isShowEmpty()) {
        // 设置了空布局且显示空布局
        if (haveHeader() && showHeaderWhenEmpty) {
            // 有头布局，且显示
            if (haveFooter() && showFooterWhenEmpty) {
                // 有脚布局，且显示
                3
            } else {
                // 无脚布局或不显示
                2
            }
        } else {
            // 无头布局或不显示
            if (haveFooter() && showFooterWhenEmpty) {
                // 有脚布局，且显示
                2
            } else {
                // 无脚布局或不显示
                1
            }
        }
    } else {
        // 未设置空布局或不显示空布局
        if (haveHeader()) {
            // 有头布局
            if (haveFooter()) {
                // 有脚布局
                if (haveLoadMore()) {
                    // 有加载更多
                    mData.size + 3
                } else {
                    // 无加载更多
                    mData.size + 2
                }
            } else {
                // 无脚布局
                if (haveLoadMore()) {
                    // 有加载更多
                    mData.size + 2
                } else {
                    // 无加载更多
                    mData.size + 1
                }
            }
        } else {
            // 无头布局
            if (haveFooter()) {
                // 有脚布局
                if (haveLoadMore()) {
                    // 有加载更多
                    mData.size + 2
                } else {
                    // 无加载更多
                    mData.size + 1
                }
            } else {
                // 无脚布局
                if (haveLoadMore()) {
                    // 有加载更多
                    mData.size + 1
                } else {
                    // 无加载更多
                    mData.size
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvDBViewHolder<DB, E> {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                // 头布局
                createViewHolder(mHeaderLayout!!)
            }
            VIEW_TYPE_FOOTER -> {
                // 脚布局
                createViewHolder(mFooterLayout!!)
            }
            VIEW_TYPE_EMPTY -> {
                // 空布局
                createViewHolder(mEmptyLayout!!)
            }
            VIEW_TYPE_LOADING -> {
                // 加载更多
                val loading = LayoutInflater.from(parent.context).inflate(mLoadMoreView!!.layoutResID(), parent, false)
                createViewHolder(loading)
            }
            else -> {
                customCreateViewHolder(parent, viewType)
            }
        }
    }

    /**
     * 除特殊布局外的其他布局，一种布局无需变化，多种布局重写
     */
    open fun customCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRvDBViewHolder<DB, E> {
        // 普通布局
        // 加载布局，初始化 DataBinding
        val binding = DataBindingUtil.inflate<DB>(
            LayoutInflater.from(parent.context),
            layoutResID(), parent, false
        )
        // 绑定事件处理
        mViewModel?.let {
            binding.setVariable(BR.model, mViewModel)
        }
        // 创建 ViewHolder
        return createViewHolder(binding)
    }

    /**
     * 除特殊布局外的其他布局，一种布局无需变化，多种布局重写
     */
    override fun onBindViewHolder(holder: BaseRvDBViewHolder<DB, E>, position: Int) {
        autoLoadMore(position)
        if (holder.itemViewType == VIEW_TYPE_LOADING) {
            mLoadMoreView!!.convert(holder)
        }
        if (needFix(position)) {
            // 头布局、脚布局、空布局，返回不做操作
            return
        }
        customBindViewHolder(holder, position)
    }

    open fun customBindViewHolder(holder: BaseRvDBViewHolder<DB, E>, position: Int) {
        // 普通布局，绑定数据
        convert(holder, getItem(if (haveHeader()) position - 1 else position))
        // DataBinding 更新布局
        holder.mBinding.executePendingBindings()
    }

    private fun autoLoadMore(position: Int) {
        if (!haveLoadMore()) {
            return
        }
        if (mData.size <= 0) {
            return
        }
        if (position < itemCount - 1) {
            return
        }
        if (mLoadMoreView!!.viewStatus != BaseRvLoadMoreView.VIEW_STATUS_DEFAULT) {
            return
        }
        mLoadMoreView!!.viewStatus = BaseRvLoadMoreView.VIEW_STATUS_LOADING
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView
        if (emptyViewLayoutResID != null) {
            setEmptyView(emptyViewLayoutResID!!)
        }
        val manager = mRecyclerView!!.layoutManager
        if (manager is GridLayoutManager) {
            // 如果是 Grid 布局
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int) =
                    if (needFix(position)) {
                        manager.spanCount
                    } else {
                        // 如果是头布局或者脚布局，独占一行
                        1
                    }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BaseRvDBViewHolder<DB, E>) {
        val lp = holder.itemView.layoutParams
        lp?.let {
            if (it is StaggeredGridLayoutManager.LayoutParams) {
                // 是瀑布流式布局
                if (needFix(holder.layoutPosition)) {
                    // 是头布局或脚布局，独占一行
                    it.isFullSpan = true
                }
            }
        }
    }

    /**
     * 判断是否需要配置独占一行
     *
     * @param position View 位置
     *
     * @return 是否需要配置独占一行
     */
    private fun needFix(position: Int) =
        getItemViewType(position) in arrayOf(VIEW_TYPE_HEADER, VIEW_TYPE_FOOTER, VIEW_TYPE_EMPTY, VIEW_TYPE_LOADING)

    /**
     * 判断是否显示空布局
     *
     * @return 是否显示空布局
     */
    protected fun isShowEmpty() = mEmptyLayout != null && mEmptyLayout!!.childCount > 0 && mData.size <= 0

    /**
     * 判断是否是空布局
     *
     * @param position View 位置
     *
     * @return 是否是空布局
     */
    protected fun isEmpty(position: Int) = if (isShowEmpty()) {
        // 显示空布局
        if (haveHeader() && showHeaderWhenEmpty) {
            // 有头布局且显示
            position == 1
        } else {
            position == 0
        }
    } else {
        // 不显示头布局
        false
    }

    /**
     * 设置空布局
     *
     * @param layoutResID 布局资源 id
     * @param parent [ViewGroup] 父控件，用于加载布局 **若使用默认 mRecyclerView 务必在 [RecyclerView]#setAdapter() 之后调用**
     */
    fun setEmptyView(@LayoutRes layoutResID: Int, parent: ViewGroup? = mRecyclerView) {
        emptyViewLayoutResID = if (parent != null) {
            // 已绑定 RecyclerView
            val empty = LayoutInflater.from(parent.context).inflate(layoutResID, parent, false)
            setEmptyView(empty)
            null
        } else {
            layoutResID
        }
    }

    /**
     * 设置空布局
     *
     * @param emptyView 空布局
     */
    fun setEmptyView(emptyView: View) {
        // 标记，是否第一次创建
        var insert = false
        if (mEmptyLayout == null) {
            // 未初始化，初始化空布局
            mEmptyLayout = FrameLayout(emptyView.context)
            // 设置布局参数
            val layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT
            )
            val lp = emptyView.layoutParams
            if (lp != null) {
                layoutParams.width = lp.width
                layoutParams.height = lp.height
            }
            mEmptyLayout!!.layoutParams = layoutParams
            // 第一次创建
            insert = true
        }
        // 添加空布局
        mEmptyLayout!!.removeAllViews()
        mEmptyLayout!!.addView(emptyView)
        if (insert) {
            // 第一次创建
            if (isShowEmpty()) {
                // 显示空布局
                var position = 0
                if (haveHeader() && showHeaderWhenEmpty) {
                    // 有头布局并且显示
                    position++
                }
                notifyItemInserted(position)
            }
        }
    }

    /**
     * 添加头布局
     *
     * @param header 头布局
     * @param index 添加位置 默认从上往下
     * @param orientation 排列方式 [LinearLayout.VERTICAL]、[LinearLayout.HORIZONTAL] 默认竖排
     *
     * @return 插入的实际位置
     */
    fun addHeaderView(header: View, index: Int = -1, orientation: Int = LinearLayout.VERTICAL): Int {
        if (mHeaderLayout == null) {
            mHeaderLayout = LinearLayout(header.context)
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderLayout!!.orientation = LinearLayout.VERTICAL
                mHeaderLayout!!.layoutParams =
                    RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            } else {
                mHeaderLayout!!.orientation = LinearLayout.HORIZONTAL
                mHeaderLayout!!.layoutParams =
                    RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
        var i = index
        val childCount = mHeaderLayout!!.childCount
        if (index < 0 || index > childCount) {
            i = childCount
        }
        mHeaderLayout!!.addView(header, i)
        val position = getHeaderViewPosition()
        if (position != -1) {
            notifyItemInserted(position)
        }
        return i
    }

    /**
     * 添加头布局
     *
     * @param footer 脚布局
     * @param index 添加位置 默认从上往下
     * @param orientation 排列方式 [LinearLayout.VERTICAL]、[LinearLayout.HORIZONTAL] 默认竖排
     *
     * @return 插入的实际位置
     */
    fun addFooterView(footer: View, index: Int = -1, orientation: Int = LinearLayout.VERTICAL): Int {
        if (mFooterLayout == null) {
            mFooterLayout = LinearLayout(footer.context)
            if (orientation == LinearLayout.VERTICAL) {
                mFooterLayout!!.orientation = LinearLayout.VERTICAL
                mFooterLayout!!.layoutParams =
                    RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            } else {
                mFooterLayout!!.orientation = LinearLayout.HORIZONTAL
                mFooterLayout!!.layoutParams =
                    RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
        }
        var i = index
        val childCount = mFooterLayout!!.childCount
        if (index < 0 || index > childCount) {
            i = childCount
        }
        mFooterLayout!!.addView(footer, i)
        val position = getFooterViewPosition()
        if (position != -1) {
            notifyItemInserted(position)
        }
        return i
    }

    fun clearFooterView() {
        mFooterLayout?.removeAllViews()
    }

    /**
     * 判断是否有头布局
     *
     * @return 是否有头布局
     */
    protected fun haveHeader() = mHeaderLayout != null && mHeaderLayout!!.childCount > 0

    /**
     * 根据下标判断是否是头布局
     *
     * @param position View 下标
     * @return 是否是头布局
     */
    protected fun isHeader(position: Int) = if (isShowEmpty()) {
        // 显示空布局
        if (haveHeader() && showHeaderWhenEmpty) {
            // 有头布局且显示
            position == 0
        } else {
            // 不显示头布局
            false
        }
    } else {
        // 不显示空布局
        haveHeader() && position == 0
    }

    /**
     * 获取头布局位置
     *
     * @return 头布局位置
     */
    protected fun getHeaderViewPosition() = if (haveHeader()) 0 else -1

    /**
     * 判断是否有脚布局
     *
     * @return 是否有脚布局
     */
    protected fun haveFooter() = mFooterLayout != null && mFooterLayout!!.childCount > 0

    /**
     * 根据下标判断是否是脚布局
     *
     * @param position View 下标
     * @return 是否是脚布局
     */
    protected fun isFooter(position: Int) = if (!haveFooter()) {
        // 没有脚布局或不显示
        false
    } else {
        // 有脚布局且显示
        if (isShowEmpty()) {
            // 显示空布局
            if (showFooterWhenEmpty) {
                // 显示脚布局
                position == itemCount - 1
            } else {
                false
            }
        } else {
            // 不显示空布局
            if (haveLoadMore()) {
                // 显示加载更多
                position == itemCount - 2
            } else {
                position == itemCount - 1
            }
        }
    }

    /**
     * 获取脚布局位置
     *
     * @return 脚布局位置
     */
    protected fun getFooterViewPosition() = if (!haveFooter() || (isShowEmpty() && !showFooterWhenEmpty)) {
        // 不显示脚布局
        -1
    } else {
        // 显示脚布局
        if (isShowEmpty() || !haveLoadMore()) {
            // 显示空布局或不显示加载更多
            itemCount - 1
        } else {
            // 不显示空布局且显示加载更多
            itemCount - 2
        }
    }

    /**
     * 判断是否有加载更多布局
     *
     * @return 是否有加载更多
     */
    protected fun haveLoadMore() = mLoadMoreView != null && mLoadMoreEnable

    /**
     * 判断是否是加载更多
     *
     * @param position View 位置
     *
     * @return 是否是加载更多
     */
    protected fun isLoading(position: Int) = if (!haveLoadMore() || isShowEmpty()) {
        // 没有加载更多布局或不显示或显示无数据
        false
    } else {
        // 有加载更多布局且显示且不显示无数据
        position == itemCount - 1
    }

    /**
     * 根据下标获取当前布局对应的数据对象
     *
     * @param position View 下标
     * @return View 对应的数据对象
     */
    protected fun getItem(position: Int) = mData[position]

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    protected abstract fun layoutResID(): Int

    /**
     * 绑定数据
     *
     * @param holder ViewHolder
     * @param entity   数据对象
     */
    protected open fun convert(holder: BaseRvDBViewHolder<DB, E>, entity: E) {
        holder.bindData(entity)
    }

    /**
     * 创建ViewHolder
     *
     * @param binding DataBinding对象
     *
     * @return ViewHolder 对象
     */
    protected open fun createViewHolder(binding: DB): VH {
        val holderConstructor = getVHClass().getConstructor(getDBClass())
        return holderConstructor.newInstance(binding)
    }

    /**
     * 创建ViewHolder 使用头布局时必须重写
     *
     * @param view View对象
     *
     * @return ViewHolder
     */
    protected open fun createViewHolder(view: View): BaseRvDBViewHolder<DB, E> {
        @Suppress("UNCHECKED_CAST")
        val clazz = getVHClass().superclass as Class<BaseRvDBViewHolder<DB, E>>
        val constructor = clazz.getConstructor(View::class.java)
        return constructor.newInstance(view)
    }

    /**
     * 获取 ViewHolder 的类
     *
     * @return ViewHolder 实际类型
     */
    @Suppress("UNCHECKED_CAST")
    private fun getVHClass() = getActualTypeList()[0] as Class<VH>

    /**
     * 获取 DataBinding 的类
     *
     * @return DataBinding 的实际类型
     */
    @Suppress("UNCHECKED_CAST")
    private fun getDBClass() = getActualTypeList()[1] as Class<DB>

    /**
     * 获取泛型实际类型列表
     */
    private fun getActualTypeList() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments

    /**
     * 设置加载更多事件
     *
     * @param listener 加载更多事件
     */
    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        mLoadMoreView?.listener = listener
    }

    /**
     * 设置加载更多事件
     *
     * @param block 加载更多事件
     */
    fun setOnLoadMoreListener(block: () -> Unit) {
        setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                block()
            }
        })
    }

    /**
     * 设置加载失败点击事件监听
     *
     * @param listener 加载失败点击事件处理
     */
    fun setOnLoadMoreFailedClickListener(listener: OnLoadMoreFailedClickListener) {
        mLoadMoreView?.failedListener = listener
    }

    /**
     * 设置加载失败点击事件监听
     *
     * @param block 加载失败点击事件处理
     */
    fun setOnLoadMoreFailedClickListener(block: () -> Unit) {
        setOnLoadMoreFailedClickListener(object : OnLoadMoreFailedClickListener {
            override fun onClick() {
                block()
            }
        })
    }

    /**
     * 设置没有更多点击事件监听
     *
     * @param listener 没有更多点击事件监听
     */
    fun setOnLoadMoreEndClickListener(listener: OnLoadMoreEndClickListener) {
        mLoadMoreView?.endListener = listener
    }

    /**
     * 设置没有更多点击事件监听
     *
     * @param block 没有更多点击事件监听
     */
    fun setOnLoadMoreEndClickListener(block: () -> Unit) {
        setOnLoadMoreEndClickListener(object : OnLoadMoreEndClickListener {
            override fun onClick() {
                block()
            }
        })
    }

    /**
     * 当数据不满一页时停用加载更多
     *
     * @param recyclerView [RecyclerView] 对象，默认 adapter 绑定的 RecyclerView
     */
    fun disableLoadMoreIfNotFullPage(recyclerView: RecyclerView? = mRecyclerView) {
        loadMoreEnd()
        if (recyclerView == null) {
            return
        }
        val manager = recyclerView.layoutManager ?: return
        if (manager is LinearLayoutManager) {
            recyclerView.postDelayed({
                if (manager.findLastCompletelyVisibleItemPosition() + 1 != itemCount) {
                    loadMoreComplete()
                }
            }, 50)
        } else if (manager is StaggeredGridLayoutManager) {
            recyclerView.postDelayed({
                val positions = IntArray(manager.spanCount)
                manager.findLastCompletelyVisibleItemPositions(positions)
                val pos = getTheBiggestNumber(positions) + 1
                if (pos != itemCount) {
                    loadMoreComplete()
                }
            }, 50)
        }
    }

    /**
     * 获取数组中最大值
     *
     * @param numbers Int 数组
     *
     * @return 数组中最大值
     */
    private fun getTheBiggestNumber(numbers: IntArray): Int {
        var tmp = -1
        if (numbers.isEmpty()) {
            return tmp
        }
        for (num in numbers) {
            if (num > tmp) {
                tmp = num
            }
        }
        return tmp
    }

    /**
     * 加载更多完成，恢复初始状态，上拉加载更多
     */
    fun loadMoreComplete() {
        mLoadMoreView?.viewStatus = BaseRvLoadMoreView.VIEW_STATUS_DEFAULT
        notifyDataSetChanged()
    }

    /**
     * 没有更多数据，不再加载更多
     */
    fun loadMoreEnd() {
        mLoadMoreView?.viewStatus = BaseRvLoadMoreView.VIEW_STATUS_END
        notifyDataSetChanged()
    }

    /**
     * 加载更多失败
     */
    fun loadMoreFailed() {
        mLoadMoreView?.viewStatus = BaseRvLoadMoreView.VIEW_STATUS_FAILED
        notifyDataSetChanged()
    }

    /**
     * 显示没有更多了
     */
    fun showNoMore() {
        mLoadMoreEnable = true
        loadMoreEnd()
    }

    fun refresh(list: ArrayList<E>) {
        mData.clear()
        mData.addAll(list)
        notifyDataSetChanged()
    }
}