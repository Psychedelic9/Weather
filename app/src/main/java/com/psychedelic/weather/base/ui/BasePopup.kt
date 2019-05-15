package com.arcb.laborunion.base.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import cn.wj.android.common.ui.popup.CommonBasePopupWindow

/**
 * PopupWindow 基类，封装了一些兼容性处理
 */
abstract class BasePopup<DB : ViewDataBinding>(context: AppCompatActivity) : CommonBasePopupWindow<DB>(context)