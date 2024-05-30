package com.sparrow.movieapp.utilis

import com.sparrow.movieapp.domain.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    val api: ApiInterface by lazy {
        Retrofit.Builder().baseUrl(Util.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)
    }
}