package com.neatroots.example.infosphere.Service

import com.neatroots.example.infosphere.Models.ImageModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/images?category=education")
    fun getImageInfo(): Call<List<ImageModel>>
}