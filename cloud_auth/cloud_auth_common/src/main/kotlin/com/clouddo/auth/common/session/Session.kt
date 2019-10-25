package com.clouddo.auth.common.session

import java.io.Serializable

/**
 * session接口
 * @author zhongming
 */
interface Session {

    companion object {
        const val WEB_TYPE = "web"
        const val MOBILE_TYPE = "mobile"
    }

    fun getId(): Serializable

    fun getAttribute(key: Any): Any?

    fun setAttribute(key: Any, value: Any): Unit

    fun removeAttribute(key: Any): Any?

    fun getTimeout(): Long

    fun setTimeout(timeout: Long): Unit

    fun getType(): String

    fun  setType(type: String)
}