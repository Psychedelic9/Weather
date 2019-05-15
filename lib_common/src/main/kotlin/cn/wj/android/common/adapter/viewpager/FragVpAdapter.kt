package cn.wj.android.common.adapter.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Fragment ViewPager 适配器类
 */
class FragVpAdapter private constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    /** Fragment 集合 */
    val mFrags = arrayListOf<Fragment>()

    private var pageTitleListener: OnPageTitleListener? = null

    override fun getCount() = mFrags.size

    override fun getPageTitle(position: Int) = pageTitleListener?.getTitle(mFrags[position], position)

    override fun getItem(position: Int) = mFrags[position]

    /**
     * FragVpAdapter 建造者类
     */
    class Builder {

        /** Fragment 集合 */
        private val mFrags = arrayListOf<Fragment>()

        /** Fragment 管理器 */
        private lateinit var fm: FragmentManager

        /** 获取标题 */
        private var pageTitleListener: OnPageTitleListener? = null

        /**
         * 绑定 Fragment 集合
         *
         * @param frags Fragment 集合
         * @return 建造者对象
         */
        fun frags(frags: List<Fragment>): Builder {
            mFrags.clear()
            mFrags.addAll(frags)
            return this
        }

        /**
         * 绑定 Fragment 管理器
         *
         * @param fm Fragment 管理器 [FragmentManager]
         * @return 建造者对象
         */
        fun manager(fm: FragmentManager): Builder {
            this.fm = fm
            return this
        }

        fun pageTitle(listener: OnPageTitleListener): Builder {
            this.pageTitleListener = listener
            return this
        }

        fun pageTitle(block: (Fragment, Int) -> String?): Builder {
            return pageTitle(object : OnPageTitleListener {
                override fun getTitle(fragment: Fragment, position: Int): String? {
                    return block(fragment, position)
                }
            })
        }

        /**
         * 生成 FragVpAdapter 对象
         *
         * @return [FragVpAdapter] 对象
         */
        fun build(): FragVpAdapter {
            val adapter = FragVpAdapter(fm)
            adapter.mFrags.clear()
            adapter.mFrags.addAll(mFrags)
            adapter.pageTitleListener = this.pageTitleListener
            return adapter
        }
    }

    interface OnPageTitleListener {
        fun getTitle(fragment: Fragment, position: Int): String?
    }
}