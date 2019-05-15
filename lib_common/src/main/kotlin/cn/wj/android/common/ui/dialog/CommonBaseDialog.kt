package cn.wj.android.common.ui.dialog

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

/**
 * Dialog 弹窗基类
 */
abstract class CommonBaseDialog
    : DialogFragment() {

    /** Context 对象 */
    protected lateinit var mContext: AppCompatActivity

    /** 根布局对象 */
    protected lateinit var mRootView: View

    /** Dialog 宽度 单位：px  */
    protected var mDialogWidth: Int = WindowManager.LayoutParams.MATCH_PARENT
    /** Dialog 高度 单位：px */
    protected var mDialogHeight: Int = WindowManager.LayoutParams.MATCH_PARENT
    /** Dialog 重心 [Gravity] */
    protected var mGravity: Int = Gravity.CENTER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 保存 Context 对象
        mContext = activity as AppCompatActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // 取消标题栏
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(0x00000000))

        mRootView = inflater.inflate(layoutResID, container, false)

        // 初始化布局
        initView()

        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 配置 Dialog 宽高、重心
        val layoutParams = dialog?.window?.attributes
        layoutParams?.width = mDialogWidth
        layoutParams?.height = mDialogHeight
        layoutParams?.gravity = mGravity
        dialog?.window?.attributes = layoutParams
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
            Log.e("Common_dialog", e.localizedMessage)
        }
    }

    /**
     * 初始化数据，最先调用
     */
    protected open fun initBefore() {}

    /**
     * 初始化布局
     */
    protected open fun initView() {}

    /** 界面布局 id */
    abstract val layoutResID: Int

    fun show(activity: AppCompatActivity, tag: String) {
        try {
            show(activity.supportFragmentManager, tag)
        } catch (e: Exception) {
            Log.e("Common_Dialog", e.localizedMessage)
        }
    }
}