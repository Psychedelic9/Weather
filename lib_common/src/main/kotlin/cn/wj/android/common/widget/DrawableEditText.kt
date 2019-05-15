package cn.wj.android.common.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import cn.wj.android.common.R
import kotlin.math.roundToInt

/**
 * 可设置 Drawable 尺寸的 TextView
 */
class DrawableEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.DrawableEditText)

            val dStart = a.getDrawable(R.styleable.DrawableEditText_det_drawableStart)
            if (dStart != null) {
                val wStart = a.getDimension(R.styleable.DrawableEditText_det_drawableStartWidth, 0f)
                val hStart = a.getDimension(R.styleable.DrawableEditText_det_drawableStartHeight, 0f)
                setTextViewDrawableStart(dStart, wStart, hStart)
            }

            val dEnd = a.getDrawable(R.styleable.DrawableEditText_det_drawableEnd)
            if (dEnd != null) {
                val wEnd = a.getDimension(R.styleable.DrawableEditText_det_drawableEndWidth, 0f)
                val hEnd = a.getDimension(R.styleable.DrawableEditText_det_drawableEndHeight, 0f)
                setTextViewDrawableEnd(dEnd, wEnd, hEnd)
            }

            val dTop = a.getDrawable(R.styleable.DrawableEditText_det_drawableTop)
            if (dTop != null) {
                val wTop = a.getDimension(R.styleable.DrawableEditText_det_drawableTopWidth, 0f)
                val hTop = a.getDimension(R.styleable.DrawableEditText_det_drawableTopHeight, 0f)
                setTextViewDrawableTop(dTop, wTop, hTop)
            }

            val dBottom = a.getDrawable(R.styleable.DrawableEditText_det_drawableBottom)
            if (dBottom != null) {
                val wBottom = a.getDimension(R.styleable.DrawableEditText_det_drawableBottomWidth, 0f)
                val hBottom = a.getDimension(R.styleable.DrawableEditText_det_drawableBottomHeight, 0f)
                setTextViewDrawableBottom(dBottom, wBottom, hBottom)
            }

            a.recycle()
        }
    }

    /**
     * 设置 TextView 右侧图片
     *
     * @param drawable 图片
     * @param width 图片宽度
     * @param height 图片高度
     */
    private fun setTextViewDrawableEnd(drawable: Drawable, width: Float, height: Float) {
        if (width > 0f && height > 0f) {
            drawable.setBounds(0, 0, width.roundToInt(), height.roundToInt())
        }
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], drawable, compoundDrawables[3])
    }

    /**
     * 设置 TextView 左侧图片
     *
     * @param drawable 图片
     * @param width 图片宽度
     * @param height 图片高度
     */
    private fun setTextViewDrawableStart(drawable: Drawable, width: Float, height: Float) {
        if (width > 0f && height > 0f) {
            drawable.setBounds(0, 0, width.roundToInt(), height.roundToInt())
        }
        setCompoundDrawables(drawable, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3])
    }

    /**
     * 设置 TextView 顶部图片
     *
     * @param drawable 图片
     * @param width 图片宽度
     * @param height 图片高度
     */
    private fun setTextViewDrawableTop(drawable: Drawable, width: Float, height: Float) {
        if (width > 0f && height > 0f) {
            drawable.setBounds(0, 0, width.roundToInt(), height.roundToInt())
        }
        setCompoundDrawables(compoundDrawables[0], drawable, compoundDrawables[2], compoundDrawables[3])
    }

    /**
     * 设置 TextView 下方图片
     *
     * @param drawable 图片
     * @param width 图片宽度
     * @param height 图片高度
     */
    private fun setTextViewDrawableBottom(drawable: Drawable, width: Float, height: Float) {
        if (width > 0f && height > 0f) {
            drawable.setBounds(0, 0, width.roundToInt(), height.roundToInt())
        }
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], drawable)
    }
}
