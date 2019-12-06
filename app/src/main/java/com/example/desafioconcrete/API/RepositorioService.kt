package com.example.desafioconcrete.API

import android.telecom.Call
import com.example.desafioconcrete.model.Resultado
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositorioService {


    /*https://api.github.com/search/repositories?q=language:Java&sort=stars&page=1*/




    @GET("repositories")
    fun recuperarVideos (
        @Query("q") q:String,
        @Query("sort") sort:String,
        @Query("page") page:Int


    ): retrofit2.Call<Resultado>


}