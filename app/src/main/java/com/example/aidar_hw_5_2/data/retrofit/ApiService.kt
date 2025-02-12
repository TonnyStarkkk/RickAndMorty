package com.example.aidar_hw_5_2.data.retrofit

import com.example.aidar_hw_5_2.data.model.characters.BaseResponse
import com.example.aidar_hw_5_2.data.model.detail.BaseDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character")
    fun getAllCharacters(): Call<BaseResponse>

    @GET("character/{id}")
    fun getSingleCharacter(
        @Path("id") id: Int
    ): Call<BaseDetailResponse>
}