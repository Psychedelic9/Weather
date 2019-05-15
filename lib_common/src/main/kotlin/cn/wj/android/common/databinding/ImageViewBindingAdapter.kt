package cn.wj.android.common.databinding

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import cn.wj.android.common.constants.IMG_DRAWABLE_MARK
import cn.wj.android.common.constants.IMG_MIPMAP_MARK
import cn.wj.android.common.constants.IMG_RESOURCE_MARK
import cn.wj.android.common.expanding.isNotNullAndBlank
import cn.wj.android.common.expanding.orFalse
import cn.wj.android.common.tools.getIdentifier
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File

/**
 * ImageView DataBinding 适配器
 */
@Suppress("unused")
class ImageViewBindingAdapter {

    companion object {

        /**
         * 根据资源 id 加载图片
         *
         * @param iv    [ImageView] 对象
         * @param resID 图片资源 id
         */
        @JvmStatic
        @BindingAdapter("android:src")
        fun src(iv: ImageView, resID: Int) {
            if (0 != resID) {
                iv.setImageResource(resID)
            }
        }

        /**
         * 加载图片
         *
         * @param iv     [ImageView] 对象
         * @param imgUrl 图片 Url
         * @param placeholder 占位图片
         * @param default 默认图片
         */
        @SuppressLint("CheckResult")
        @JvmStatic
        @BindingAdapter(
            "android:bind_iv_img_url",
            "android:bind_iv_img_placeholder",
            "android:bind_iv_img_default",
            "android:bind_iv_img_circle",
            requireAll = false
        )
        fun setImageViewUrl(
            iv: ImageView,
            imgUrl: String?,
            placeholder: Drawable?,
            default: Drawable?,
            circle: Boolean?
        ) {
            val options = RequestOptions()
            if (null != placeholder) {
                options.placeholder(placeholder)
            }
            if (null != default) {
                options.error(default)
            }
            if (circle.orFalse()) {
                options.circleCrop()
            }
            Glide.with(iv.context)
                .load(imgUrl)
                .apply(options)
                .into(iv)
        }

        /**
         * 加载图片
         *
         * @param iv     [ImageView] 对象
         * @param path 图片地址
         * @param placeholder 占位图片
         * @param default 默认图片
         */
        @SuppressLint("CheckResult")
        @JvmStatic
        @BindingAdapter(
            "android:bind_iv_img_path",
            "android:bind_iv_img_placeholder",
            "android:bind_iv_img_default",
            "android:bind_iv_img_circle",
            requireAll = false
        )
        fun setImageViewPath(
            iv: ImageView,
            path: String?,
            placeholder: Drawable?,
            default: Drawable?,
            circle: Boolean?
        ) {
            val options = RequestOptions()
            if (null != placeholder) {
                options.placeholder(placeholder)
            }
            if (null != default) {
                options.error(default)
            }
            if (circle.orFalse()) {
                options.circleCrop()
            }
            if (path.isNullOrBlank()) {
                Glide.with(iv.context)
                    .load(default)
            } else {
                Glide.with(iv.context)
                    .load(File(path))
            }
                .apply(options)
                .into(iv)
        }

        /**
         * 加载图片
         *
         * @param iv     [ImageView] 对象
         * @param img 图片路径
         * @param placeholder 占位图片
         * @param default 默认图片
         */
        @SuppressLint("CheckResult")
        @JvmStatic
        @BindingAdapter(
            "android:bind_iv_img",
            "android:bind_iv_img_placeholder",
            "android:bind_iv_img_default",
            "android:bind_iv_img_circle",
            requireAll = false
        )
        fun setImageViewImg(iv: ImageView, img: String?, placeholder: Drawable?, default: Drawable?, circle: Boolean?) {
            if (img.isNotNullAndBlank()) {
                if (img!!.contains("http:") || img.contains("https:")) {
                    // url
                    setImageViewUrl(iv, img, placeholder, default, circle)
                } else if (img.contains(IMG_RESOURCE_MARK)) {
                    // Resource
                    setImageResource(iv, img)
                } else {
                    // path
                    setImageViewPath(iv, img, placeholder, default, circle)
                }
            } else {
                val options = RequestOptions()
                if (null != placeholder) {
                    options.placeholder(placeholder)
                }
                if (null != default) {
                    options.error(default)
                }
                if (circle.orFalse()) {
                    options.circleCrop()
                }
                Glide.with(iv)
                    .load(default)
                    .apply(options)
                    .into(iv)
            }
        }

        /**
         * 根据 id 字符串加载图片
         *
         * @param iv [ImageView] 对象
         * @param res 资源字符串 drawable-anydpi-resource:resId 或 mipmap-resource:resId
         */
        @JvmStatic
        @BindingAdapter("android:bind_src")
        fun setImageResource(iv: ImageView, res: String) {
            if (res.isNotBlank()) {
                if (res.startsWith(IMG_DRAWABLE_MARK)) {
                    val realRes = res.split(":")[1]
                    iv.setImageResource(realRes.getIdentifier(iv.context, "drawable"))
                } else if (res.startsWith(IMG_MIPMAP_MARK)) {
                    val realRes = res.split(":")[1]
                    iv.setImageResource(realRes.getIdentifier(iv.context, "mipmap"))
                }
            }
        }
    }
}