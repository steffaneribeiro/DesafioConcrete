package com.example.desafioconcrete.retrofit.helper

import com.example.desafioconcrete.retrofit.service.RepositorioService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoriesConfig{
    private val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/search/")
            .addConverterFactory(GsonConverterFactory.create()).build()

    fun createRetrofit() = retrofit.create(RepositorioService::class.java)

}