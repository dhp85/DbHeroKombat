package com.keepcoding.dragonball.repository

import android.util.Log
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class UserRepository {

    companion object {
        private var token = ""
    }

    private val BASE_URL = "https://dragonball.keepcoding.education/api/"

    sealed class LoginResponse {
        data object Success: LoginResponse()
        data class Error(val message: String) : LoginResponse()
    }

    fun login(user: String, password: String): LoginResponse {

        val client = OkHttpClient()
        val url = "${BASE_URL}auth/login"

        val credentials = Credentials.basic(user,password)
        val formBody = FormBody.Builder()
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .addHeader("Authorization", credentials)
            .build()

        val response = client.newCall(request).execute()

        Log.d("UserRepository", "Response code: ${response.code}")
        Log.d("UserRepository", "Response message: ${response.message}")

        return if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val tokenReceived = responseBody?.trim() ?: ""
            token = tokenReceived
            LoginResponse.Success

        } else {
            Log.e("UserRepository", "Error en la autenticación: ${response.code} - ${response.message}")
            LoginResponse.Error("Error ${response.code}: ${response.message}")
        }

    }

    fun getToken(): String {
        return token
    }
}