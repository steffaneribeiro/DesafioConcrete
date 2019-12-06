package com.example.desafioconcrete.helper

import android.util.Log
import com.example.desafioconcrete.model.Item
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoriesConfig (){


    fun getRetrofit() : Retrofit{
        return Retrofit.Builder().baseUrl("https://api.github.com/search/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}