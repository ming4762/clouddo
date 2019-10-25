package com.clouddo.auth.common.model

import java.io.Serializable
import java.util.*
import javax.persistence.Table


@Table(name = "sys_user")
class User : Serializable {

    companion object {
        private const val serialVersionUID = -1404236215225147864L
    }

    var userId: String? = null

    var username: String? = null

    var name: String? = null

    var password: String? = null

    var email: String? = null

    var mobile: String? = null

    var status: String? = null

    var createUserId: String? = null

    var createTime: Date? = null

    var updateUserId: String? = null

    var updateTime: Date? = null

    var sex: Int? = null

    var picId: String? = null

    var seq: Int? = 0
}