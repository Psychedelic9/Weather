package cn.wj.android.common.databinding

import androidx.databinding.BindingAdapter
import cn.wj.android.common.expanding.orFalse
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * BottomNavigationView DataBinding 适配器
 */
@Suppress("unused")
class BottomNavigationViewAdapter {

    companion object {

        /**
         * 设置条目选中监听
         *
         * @param bnv [BottomNavigationView] 对象
         * @param itemSelected 条目选中回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_bnv_onItemSelected")
        fun setOnNavigationItemSelectedListener(bnv: BottomNavigationView, itemSelected: ((Int) -> Boolean)?) {
            bnv.setOnNavigationItemSelectedListener {
                itemSelected?.invoke(it.itemId).orFalse()
            }
        }

//        /**
//         * 关闭默认动画
//         * - library 28 以下使用
//         *
//         * @param bnv [BottomNavigationView] 对象
//         * @param disable 是否关闭
//         */
//        @JvmStatic
//        @BindingAdapter("android:bind_bnv_disableShifMode")
//        fun disableShifMode(bnv: BottomNavigationView, disable: Boolean?) {
//            if (disable.orFalse()) {
//                bnv.disableShiftMode()
//            }
//        }
    }
}