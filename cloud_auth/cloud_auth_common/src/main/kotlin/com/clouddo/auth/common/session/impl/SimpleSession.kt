package com.clouddo.auth.common.session.impl

import com.clouddo.auth.common.session.Session
import java.io.Serializable

class SimpleSession : Session, Serializable {


    companion object {
        private const val serialVersionUID: Long = -5899328882011544902L
    }

    private lateinit var id: Serializable
    private var timeout: Long = 3600
    private var attributes: MutableMap<Any, Any> = hashMapOf()
    private var type = Session.WEB_TYPE

    constructor()

    constructor(id: Serializable) {
        this.id = id
    }

    constructor(id: Serializable, type: String) {
        this.id = id
        this.type = type
    }

    /**
     * 获取session ID
     */
    override fun getId(): Serializable {
        return this.id
    }

    /**
     * 获取内容
     */
    override fun getAttribute(key: Any): Any? {
        return this.attributes.get(key)
    }

    /**
     * 设置属性
     */
    override fun setAttribute(key: Any, value: Any) {
        this.attributes.put(key, value)
    }

    /**
     * 移除元素
     */
    override fun removeAttribute(key: Any): Any? {
        return this.attributes.remove(key)
    }

    override fun getTimeout(): Long {
        return this.timeout
    }

    override fun setTimeout(timeout: Long) {
        this.timeout = timeout
    }

    override fun getType(): String {
        return this.type
    }

    override fun setType(type: String) {
        this.type = type
    }

}