package com.example.desafioconcrete.API

import com.example.desafioconcrete.model.PullRequest
import com.example.desafioconcrete.model.Resultado
import retrofit2.http.GET
import retrofit2.http.Path

interface PullRequestsService {

    @GET("{login}/{name}/pulls")
    fun recuperarPullRequests ( @Path("name") name:String,@Path("login") login:String): retrofit2.Call<List<PullRequest>>
}