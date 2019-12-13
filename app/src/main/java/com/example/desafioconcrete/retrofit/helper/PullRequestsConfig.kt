package com.example.desafioconcrete.retrofit.helper

import com.example.desafioconcrete.retrofit.service.RepositorioService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PullRequestsConfig (){
    private var retrofit = Retrofit.Builder().baseUrl("https://api.github.com/repos/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    fun createRetrofitPullRequesdts() = retrofit.create(RepositorioService::class.java)
}