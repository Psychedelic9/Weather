package cn.wj.android.common.expanding

///**
// * 关闭切换动画效果
// * - library 28 以下使用
// */
//@SuppressLint("RestrictedApi")
//fun BottomNavigationView.disableShiftMode() {
//    val menuView = this.getChildAt(0) as BottomNavigationMenuView
//    try {
//        val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
//        shiftingMode.isAccessible = true
//        shiftingMode.setBoolean(menuView, false)
//        shiftingMode.isAccessible = false
//        for (i in 0 until menuView.childCount) {
//            val item = menuView.getChildAt(i) as BottomNavigationItemView
//            item.setShiftingMode(false)
//            item.setChecked(item.itemData.isChecked)
//        }
//    } catch (e: NoSuchFieldException) {
//        Log.e("Common_BottomNavigation", "Unable to get shift mode field", e)
//    } catch (e: IllegalAccessException) {
//        Log.e("Common_BottomNavigation", "Unable to change value of shift mode", e)
//    }
//}
