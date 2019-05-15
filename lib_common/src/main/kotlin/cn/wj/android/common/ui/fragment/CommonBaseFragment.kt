package cn.wj.android.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Fragment基类
 */
abstract class CommonBaseFragment
    : Fragment() {

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: AppCompatActivity

    /** 跟布局对象 */
    protected lateinit var mRootView: View

    /** 页面标题 */
    open val mPageTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 保存当前 Context 对象
        mContext = activity as AppCompatActivity

        // 初始化数据
        initBefore()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mRootView = inflater.inflate(layoutResID, container, false)

        // 初始化布局
        initView()

        return mRootView
    }

    /**
     * 初始化数据，最先调用
     */
    protected open fun initBefore() {}

    /** 界面布局 id */
    abstract val layoutResID: Int

    /**
     * 初始化布局
     */
    abstract fun initView()
}