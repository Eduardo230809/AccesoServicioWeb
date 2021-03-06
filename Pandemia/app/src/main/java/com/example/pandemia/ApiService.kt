package com.example.pandemia

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getDatosPandemia(@Url url:String): Response<ModelPandemia>
}