package com.mfm.mockforme.service

import com.mfm.mockforme.model.User
import retrofit2.http.GET

interface UserService {
    @GET("/users")
    suspend fun getUsers(): List<User>
}