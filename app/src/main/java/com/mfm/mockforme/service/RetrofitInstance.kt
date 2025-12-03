package com.mfm.mockforme.service

import com.mockforme.MockformeInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private fun getInstance(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(MockformeInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getUserService(): UserService  {
        return getInstance().create(UserService::class.java)
    }
}