package cn.wj.android.common.databinding

import android.animation.AnimatorInflater
import android.graphics.Color
import android.os.Build
import android.view.MotionEvent
import android.view.View
import androidx.core.view.ViewCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import cn.wj.android.common.expanding.orFalse
import cn.wj.android.common.expanding.orTrue

/**
 * View DataBinding 适配器
 */
@Suppress("unused")
class ViewBindingAdapter {

    companion object {

        /**
         * 设置点击事件
         *
         * @param v [View] 对象
         * @param click 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onClick")
        fun setViewOnClick(v: View, click: ((View) -> Unit)?) {
            v.setOnClickListener(click)
        }

        /**
         * 设置点击事件
         *
         * @param v [View] 对象
         * @param click 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onClick")
        fun setViewOnClick(v: View, click: (() -> Unit)?) {
            v.setOnClickListener {
                click?.invoke()
            }
        }

        /**
         * 设置点击事件
         *
         * @param v [View] 对象
         * @param click 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onClick", "android:bind_onClick_item")
        fun <T> setViewOnClick(v: View, click: ((View, T) -> Unit)?, item: T) {
            v.setOnClickListener { view -> click?.invoke(view, item) }
        }

        /**
         * 设置点击事件
         *
         * @param v [View] 对象
         * @param click 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onClick", "android:bind_onClick_item")
        fun <T> setViewOnClick(v: View, click: ((T) -> Unit)?, item: T) {
            v.setOnClickListener { click?.invoke(item) }
        }

        /**
         * 设置点击事件
         *
         * @param v [View] 对象
         * @param listener 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onClick")
        fun setViewOnClick(v: View, listener: View.OnClickListener?) {
            v.setOnClickListener(listener)
        }

        /**
         * 设置长点击事件
         *
         * @param v [View] 对象
         * @param click 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onLongClick")
        fun setViewOnLongClick(v: View, click: ((View) -> Boolean)?) {
            v.setOnLongClickListener(click)
        }

        /**
         * 设置长点击事件
         *
         * @param v [View] 对象
         * @param click 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onLongClick")
        fun setViewOnLongClick(v: View, click: (() -> Boolean)?) {
            v.setOnLongClickListener { click?.invoke().orFalse() }
        }

        /**
         * 设置长点击事件
         *
         * @param v [View] 对象
         * @param click 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onLongClick", "android:bind_onLongClick_item")
        fun <T> setViewOnLongClick(v: View, click: ((View, T) -> Boolean)?, item: T) {
            v.setOnLongClickListener { click?.invoke(it, item).orFalse() }
        }

        /**
         * 设置长点击事件
         *
         * @param v [View] 对象
         * @param click 点击回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onLongClick", "android:bind_onLongClick_item")
        fun <T> setViewOnLongClick(v: View, click: ((T) -> Boolean)?, item: T) {
            v.setOnLongClickListener { click?.invoke(item).orFalse() }
        }

        /**
         * 设置 View 显示
         *
         * @param v [View] 对象
         * @param visibility 显示状态
         */
        @JvmStatic
        @BindingAdapter("android:bind_visibility")
        fun setViewVisibility(v: View, visibility: Int) {
            v.visibility = visibility
        }

        /**
         * 设置 View 显示
         *
         * @param v [View] 对象
         * @param show 是否显示
         */
        @JvmStatic
        @BindingAdapter("android:bind_visibility", "android:bind_visibility_gone", requireAll = false)
        fun setViewVisibility(v: View, show: Boolean, gone: Boolean? = true) {
            v.visibility = if (show) View.VISIBLE else if (gone.orTrue()) View.GONE else View.INVISIBLE
        }

        /**
         * 设置 View 选中
         *
         * @param v [View] 对象
         * @param selected 是否选中
         */
        @JvmStatic
        @BindingAdapter("android:bind_selected")
        fun setViewSelected(v: View, selected: Boolean) {
            v.isSelected = selected
        }

        /**
         * 设置 View 是否允许点击
         *
         * @param v [View] 对象
         * @param enable 是否允许点击
         */
        @JvmStatic
        @BindingAdapter("android:bind_enable")
        fun setViewEnable(v: View, enable: Boolean) {
            v.isEnabled = enable
        }

        /**
         * 设置 View 是否获取焦点
         *
         * @param v [View] 对象
         * @param focusable 是否获取焦点
         */
        @JvmStatic
        @BindingAdapter("android:bind_focusable", "android:bind_focusableAttrChanged", requireAll = false)
        fun setViewFocusable(v: View, focusable: Boolean, listener: InverseBindingListener?) {
            v.isFocusable = focusable
            listener?.onChange()
        }

        /**
         * 获取焦点状态
         *
         * @param v [View] 对象
         *
         * @return 焦点状态
         */
        @JvmStatic
        @InverseBindingAdapter(attribute = "android:bind_focusable")
        fun getViewFocusable(v: View): Boolean {
            return v.isFocusable
        }

        /**
         * 设置 View 焦点变化监听
         *
         * @param v [View] 对象
         * @param change 监听回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_focus_change", "android:bind_focusableAttrChanged", requireAll = false)
        fun setViewFocusableListener(v: View, change: (Boolean) -> Unit, listener: InverseBindingListener?) {
            v.setOnFocusChangeListener { _, hasFocus ->
                change.invoke(hasFocus)
                listener?.onChange()
            }
        }

        /**
         * 设置 View 焦点变化监听
         *
         * @param v [View] 对象
         * @param change 监听回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_focus_change", "android:bind_focusableAttrChanged", requireAll = false)
        fun setViewFocusableListener(v: View, change: (View, Boolean) -> Unit, listener: InverseBindingListener?) {
            v.setOnFocusChangeListener { _, hasFocus ->
                change.invoke(v, hasFocus)
                listener?.onChange()
            }
        }

        /**
         * 设置 View 布局监听
         *
         * @param v [View] 对象
         * @param onGlobal 回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onGlobal")
        fun setViewOnGlobal(v: View, onGlobal: ((View) -> Unit)?) {
            v.viewTreeObserver.addOnGlobalLayoutListener {
                onGlobal?.invoke(v)
            }
        }

        /**
         * 设置 View 布局监听
         *
         * @param v [View] 对象
         * @param onGlobal 回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onGlobal")
        fun setViewOnGlobal(v: View, onGlobal: (() -> Unit)?) {
            v.viewTreeObserver.addOnGlobalLayoutListener {
                onGlobal?.invoke()
            }
        }

        /**
         * 设置 View 触摸监听
         *
         * @param v [View] 对象
         * @param onTouch 回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onTouch", "android:bind_onTouch_item")
        fun <T> setViewOnTouch(v: View, onTouch: ((View, MotionEvent, T) -> Boolean)?, item: T) {
            v.setOnTouchListener { _, event ->
                onTouch?.invoke(v, event, item).orFalse()
            }
        }

        /**
         * 设置 View 触摸监听
         *
         * @param v [View] 对象
         * @param onTouch 回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onTouch")
        fun setViewOnTouch(v: View, onTouch: ((View, MotionEvent) -> Boolean)?) {
            v.setOnTouchListener(onTouch)
        }

        /**
         * 设置 View 触摸监听
         *
         * @param v [View] 对象
         * @param onTouch 回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onTouch")
        fun setViewOnTouch(v: View, onTouch: ((MotionEvent) -> Boolean)?) {
            v.setOnTouchListener { _, ev ->
                onTouch?.invoke(ev).orFalse()
            }
        }

        /**
         * 设置 View 触摸监听
         *
         * @param v [View] 对象
         * @param onTouch 回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onTouch")
        fun setViewOnTouch(v: View, onTouch: (() -> Boolean)?) {
            v.setOnTouchListener { _, _ ->
                onTouch?.invoke().orFalse()
            }
        }

        /**
         * 设置 View 触摸监听
         *
         * @param v [View] 对象
         * @param onTouch 回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onTouch", "android:bind_onTouch_item")
        fun <T> setViewOnTouch(v: View, onTouch: ((MotionEvent, T) -> Boolean)?, item: T) {
            v.setOnTouchListener { _, ev ->
                onTouch?.invoke(ev, item).orFalse()
            }
        }

        /**
         * 设置 View 触摸监听
         *
         * @param v [View] 对象
         * @param onTouch 回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_onTouch", "android:bind_onTouch_item")
        fun <T> setViewOnTouch(v: View, onTouch: ((T) -> Boolean)?, item: T) {
            v.setOnTouchListener { _, _ ->
                onTouch?.invoke(item).orFalse()
            }
        }

        /**
         * 根据字符串颜色值设置背景色
         *
         * @param v [View] 对象
         * @param color 颜色值，如：**#FFFFFF**
         */
        @JvmStatic
        @BindingAdapter("android:bind_background")
        fun setBackground(v: View, color: String?) {
            if (!color.isNullOrEmpty()) {
                v.setBackgroundColor(Color.parseColor(color))
            }
        }

        /**
         * 根据资源 id 设置背景
         *
         * @param v [View] 对象
         * @param resID 背景资源 id
         */
        @JvmStatic
        @BindingAdapter("android:bind_background")
        fun setBackgroundRes(v: View, resID: Int) {
            if (0 == resID) {
                v.background = null
            } else {
                v.setBackgroundResource(resID)
            }
        }

        /**
         * 设置 View 海拔高度
         * - 仅 API >= LOLLIPOP 有效
         *
         * @param elevation 高度 单位:dp
         */
        @JvmStatic
        @BindingAdapter("android:bind_elevation")
        fun setElevation(v: View, elevation: Float) {
            ViewCompat.setElevation(v, elevation)
        }

        /**
         * 设置 View 动画列表
         * - 仅 API >= LOLLIPOP 有效
         *
         * @param anmatorId 动画列表 id
         */
        @JvmStatic
        @BindingAdapter("android:bind_stateListAnimator")
        fun setStateListAnimator(v: View, anmatorId: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                v.stateListAnimator = AnimatorInflater.loadStateListAnimator(v.context, anmatorId)
            }
        }
    }
}