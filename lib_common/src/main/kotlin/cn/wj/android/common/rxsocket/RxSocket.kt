package cn.wj.android.common.rxsocket

import cn.wj.android.common.tools.toEntity
import cn.wj.android.common.tools.toJson
import io.reactivex.Observable
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

/**
 * 支持 Rx 的 Socket 帮助类
 */
class RxSocket {

    /** 主机地址 */
    var mHost = ""
    /** 端口号 */
    var mPort = 8080
    /** 数据流读取超时时间 */
    var mSoTimeout = 0
    /** 确认前是否缓存 */
    var mEnableNoDelay = false
    /** 延时关闭时间 */
    var mSoLinger = 0
    /** 发送缓冲区大小 */
    var mSendBufferSize = 4096
    /** 是否保存长连接 */
    var mKeepAlive = false

    /**
     * 获取 Socket 对象
     */
    private fun socket(): Socket {
        if (mHost.isBlank()) {
            throw RuntimeException("Please set Host First!")
        }
        return Socket(mHost, mPort).apply {
            soTimeout = mSoTimeout
            tcpNoDelay = mEnableNoDelay
            setSoLinger(mSoLinger > 0, mSoLinger)
            sendBufferSize = mSendBufferSize
            keepAlive = mKeepAlive
        }
    }

    /**
     * 发送请求
     */
    fun <T> execute(bean: Any): Observable<T> {
        return Observable.create<T> { oe ->
            // 获取 Socket 对戏
            val socket = socket()
            // 发送到服务器
            val ou = socket.getOutputStream()
            ou.write(bean.toJson().toByteArray())
            ou.flush()
            // 读取服务器返回的数据
            val br = BufferedReader(InputStreamReader(socket.getInputStream()))
            val sb = StringBuilder()
            var str: String?
            while (true) {
                str = br.readLine()
                if (str == null) {
                    break
                }
                sb.append(str)
            }
            br.close()
            socket.close()
            val i = sb.toString().toEntity<T>()
            if (i == null) {
                oe.onError(RuntimeException("Json Convert Error!"))
            } else {
                oe.onNext(i)
            }
            oe.onComplete()
        }
    }

    class Builder {

        /** 主机地址 */
        var host = ""
        /** 端口号 */
        var port = 8080
        /** 数据流读取超时时间 */
        var soTimeout = 0
        /** 确认前是否缓存 */
        var enableNoDelay = false
        /** 延时关闭时间 */
        var soLinger = 0
        /** 发送缓冲区大小 */
        var sendBufferSize = 4096
        /** 是否保存长连接 */
        var keepAlive = false

        constructor()

        constructor(socket: RxSocket) {
            host = socket.mHost
            port = socket.mPort
            soTimeout = socket.mSoTimeout
            enableNoDelay = socket.mEnableNoDelay
            soLinger = socket.mSoLinger
            sendBufferSize = socket.mSendBufferSize
            keepAlive = socket.mKeepAlive
        }

        /**
         * 设置主机地址
         */
        fun setHost(host: String): Builder {
            this.host = host
            return this
        }

        /**
         * 设置端口号
         */
        fun setPort(port: Int): Builder {
            this.port = port
            return this
        }

        /**
         * 设置数据流读取超时时间
         */
        fun setSoTimeout(timeout: Int): Builder {
            this.soTimeout = timeout
            return this
        }

        /**
         * 设置确认前是否缓存
         */
        fun setEnableNoDelay(enable: Boolean): Builder {
            this.enableNoDelay = enable
            return this
        }

        /**
         * 设置延时关闭时间
         */
        fun setSoLinger(timeout: Int): Builder {
            this.soLinger = timeout
            return this
        }

        /**
         * 设置发送缓冲区大小
         */
        fun setSendBufferSize(size: Int): Builder {
            this.sendBufferSize = size
            return this
        }

        /**
         * 设置是否保存长连接
         */
        fun setKeepAlive(keep: Boolean): Builder {
            this.keepAlive = keep
            return this
        }

        /**
         * 构建 RxSocket 对象
         */
        fun build(): RxSocket {
            if (host.isBlank()) {
                throw RuntimeException("Please set Host First!")
            }
            return RxSocket().apply {
                mHost = host
                mPort = port
                mSoTimeout = soTimeout
                mEnableNoDelay = enableNoDelay
                mSoLinger = soLinger
                mSendBufferSize = sendBufferSize
                mKeepAlive = keepAlive
            }
        }
    }
}