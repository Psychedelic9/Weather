package com.psychedelic.weather.constents

/**
 *@Author: yiqing
 *@CreateDate: 2019/5/9 10:49
 *@UpdateDate: 2019/5/9 10:49
 *@Description:
 *@ClassName: Constant
 */
/** Logger 全局标记 */
const val LOGGER_TAG = "logger_psychedelic"

/** 启动页延时时间 单位：毫秒 */
const val SPLASH_DELAY = 200L

/** OkHttp 网络请求超时时间 */
const val NET_CONNECT_TIME_OUT = 10L
const val NET_WRITE_TIME_OUT = 30L
const val NET_READ_TIME_OUT = 30L

/** 网络请求缓存大小 100M */
const val NET_CACHE_SIZE = 1024L * 1024L * 100L

/** 网络请求返回码 - 请求成功 */
const val NET_RESPONSE_CODE_SUCCESS = 0

/** 用户类型 - 银行 */
const val USER_TYPE_BANK = "user_type_bank"
/** 用户类型 - 商户 */
const val USER_TYPE_MERCHANT = "user_type_merchant"

/** 密码最低长度 */
const val PASSWORD_MIN_LENGTH = 6

/** SP key - 是否登录 */
const val SP_IS_LOGIN = "sp_is_login"
/** SP key - Token */
const val SP_TOKEN = "sp_token"
/** SP key - 用户类型 */
const val SP_USER_TYPE = "sp_user_type"

/** 筛选类型 - 全部 */
const val FILTER_TYPE_ALL = "filter_type_all"
/** 筛选类型 - 银行卡 */
const val FILTER_TYPE_BANK_CARD = "filter_type_bank_card"
/** 筛选类型 - 扫码 */
const val FILTER_TYPE_SCAN_CODE = "filter_type_scan_code"
/** 筛选类型 - 支付宝 */
const val FILTER_TYPE_ALIPAY = "filter_type_alipay"
/** 筛选类型 - 微信 */
const val FILTER_TYPE_WECHAT = "filter_type_wechat"
/** 筛选类型 - 云闪付 */
const val FILTER_TYPE_CLOUD_PAY = "filter_type_cloud_pay"