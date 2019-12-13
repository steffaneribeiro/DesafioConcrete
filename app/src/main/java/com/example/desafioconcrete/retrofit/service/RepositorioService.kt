package com.example.desafioconcrete.retrofit.service

import com.example.desafioconcrete.model.PullRequest
import com.example.desafioconcrete.model.Resultado
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositorioService {

    @GET("repositories")
    fun recuperarRepositorios (
        @Query("q") q:String = "language:Java",
        @Query("sort") sort:String = "stars",
        @Query("page") page:Int
    ): retrofit2.Call<Resultado>

    @GET("{login}/{name}/pulls")
    fun recuperarPullRequests (
        @Path("name") name:String,
        @Path("login") login:String
    ): retrofit2.Call<List<PullRequest>>

}