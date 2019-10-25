package com.clouddo.auth.server.model

import java.io.Serializable

class JWTUser(username: String, userId: String, name: String): Serializable {

    companion object {
        private const val serialVersionUID = 292603672140890700L
    }

    lateinit var username: String
    lateinit var userId: String
    lateinit var name: String

    init {
        this.username = username
        this.userId = userId
        this.name = name
    }

}