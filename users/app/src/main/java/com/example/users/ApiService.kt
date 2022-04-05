package com.example.users

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getDatosUsers(@Url url:String): Response<ModelUsers>
}