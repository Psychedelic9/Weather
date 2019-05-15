package cn.wj.android.common.tools

import android.util.Log
import cn.wj.android.common.constants.DATE_FORMAT_DEFAULT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/* ----------------------------------------------------------------------------------------- */
/* |                                        时间相关                                        | */
/* ----------------------------------------------------------------------------------------- */

/**
 * 格式化日期、时间
 *
 * @param format 日期时间格式
 */
fun <N : Number> N.dateFormat(format: String = DATE_FORMAT_DEFAULT): String {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).format(this)
    } catch (e: ParseException) {
        Log.e("Common_Date---->>", "DATE_FORMAT", e)
        ""
    }
}

/**
 * 格式化日期、时间
 *
 * @param format 日期时间格式
 */
fun Date.dateFormat(format: String = DATE_FORMAT_DEFAULT): String {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).format(this)
    } catch (e: ParseException) {
        Log.e("Common_Date---->>", "DATE_FORMAT", e)
        ""
    }
}

/**
 * 将时间字符串格式化为指定类型
 */
fun String.paresDate(format: String): Date {
    return try {
        SimpleDateFormat(format, Locale.getDefault()).parse(this)
    } catch (e: ParseException) {
        Log.e("Common_Date---->>", "PARES_DATE", e)
        Date()
    }
}

/**
 * 将字符串时间转换为 Long 类型时间
 */
fun String?.toLongTime(format: String = DATE_FORMAT_DEFAULT): Long {
    return if (this.isNullOrEmpty()) {
        Date().time
    } else {
        try {
            SimpleDateFormat(format, Locale.getDefault()).parse(this).time
        } catch (e: ParseException) {
            Log.e("Common_Date---->>", "TO_LONG_TIME", e)
            Date().time
        }
    }
}