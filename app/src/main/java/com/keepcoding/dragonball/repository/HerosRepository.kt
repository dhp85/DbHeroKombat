package com.keepcoding.dragonball.repository

import com.google.gson.Gson
import com.keepcoding.dragonball.model.HeroModel
import com.keepcoding.dragonball.model.HeroModelDto
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class HerosRepository {

    private var herosList = listOf<HeroModel>()
    private  val BASE_URL = "https://dragonball.keepcoding.education/api/"

    sealed class HerosResponse {
        data class Success(val heros: List<HeroModel>) : HerosResponse()
        data class Error(val message: String) : HerosResponse()
    }

    fun fetchHeros(token: String): HerosResponse {

        if (herosList.isNotEmpty())
            return HerosResponse.Success(herosList)
        val client = OkHttpClient()
        val url = "${BASE_URL}heros/all"

        val formBody = FormBody.Builder()
            .add("name", "")
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .addHeader("Authorization","Bearer $token")
            .build()

        val call = client.newCall(request)
        val response = call.execute()

        return if (response.isSuccessful) {
            val herosDto: Array<HeroModelDto> =
                Gson().fromJson(response.body?.string(), Array<HeroModelDto>::class.java)

            herosList = herosDto.map {
                HeroModel(id = it.id,
                    name = it.name,
                    photo = it.photo,
                    currentLife = 100,
                    maxLife =  100
                )
            }

            HerosResponse.Success(herosList)
        } else {
            HerosResponse.Error("Error when downloading heroes.${response.message}")
        }
    }
}