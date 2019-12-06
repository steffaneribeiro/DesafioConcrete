package com.example.desafioconcrete.helper

import com.example.desafioconcrete.model.Item
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PullRequestsConfig (){

    fun getRetrofit_PullRequesdts() : Retrofit {
        return Retrofit.Builder().baseUrl("https://api.github.com/repos/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}