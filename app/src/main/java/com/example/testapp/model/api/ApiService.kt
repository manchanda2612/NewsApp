package com.example.testapp.model.api

import com.example.testapp.model.pojo.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadLines(
        @QueryMap map: HashMap<String, String>,
    ): Response<ApiResponse>
}