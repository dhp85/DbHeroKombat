package com.keepcoding.dragonball.repository

import kotlin.random.Random

class UserRepository {

    companion object {
        private var token = ""
    }

    sealed class LoginResponse {
        data object Success : LoginResponse()
        data class Error(val message: String) : LoginResponse()
    }

    fun login(usuario: String, password: String): LoginResponse {
        return if (Random.nextBoolean()) {
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImtpZCI6InByaXZhdGUifQ.eyJlbWFpbCI6ImRpZWdvaHA4NUBnbWFpbC5jb20iLCJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiaWRlbnRpZnkiOiJGQkY5QkUzRi02QzgwLTQ2MUItOUFFMC1DMDUxOUI0OEYxRjcifQ.dpPlw3yRmS_6shSCOwL1zYzt681cnh2qim4jY7NO5m8"
            LoginResponse.Success
        } else {
            LoginResponse.Error("response 501")
        }

    }

    fun getToken(): String {
        return token
    }
}