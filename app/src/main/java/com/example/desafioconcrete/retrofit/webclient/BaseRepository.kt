package com.example.desafioconcrete.retrofit.webclient

import androidx.lifecycle.MutableLiveData
import com.example.desafioconcrete.R
import com.example.desafioconcrete.model.PullRequest
import com.example.desafioconcrete.model.Resultado
import com.example.desafioconcrete.retrofit.helper.PullRequestsConfig
import com.example.desafioconcrete.retrofit.helper.RepositoriesConfig
import com.example.desafioconcrete.retrofit.service.RepositorioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BaseRepository {

    fun getRepositories(page:Int,success: (Resultado?) -> Unit, fail:(String?) -> Unit): MutableLiveData<Resultado> {

        val items = MutableLiveData<Resultado>()

        var call = RepositoriesConfig().createRetrofit()
        call.recuperarRepositorios(page = page).enqueue(object : Callback<Resultado> {
            override fun onFailure(call: Call<Resultado>, t: Throwable) {
                fail(t.message)

            }

            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {
                if (response.code() == 200) {
                    val resposta = response.body()

                    if(resposta?.total_count!=0){
                        success(resposta)
                    }else{
                        fail("Nothing Found")
                    }
                }
            }
        })

        return items
    }

}
class BasePullRequest {

    fun getPullRequest(nome:String,login:String, success: (List<PullRequest>?) -> Unit, fail:(String?) -> Unit): MutableLiveData<List<PullRequest>> {

        val items = MutableLiveData<List<PullRequest>>()

        var call = PullRequestsConfig().createRetrofitPullRequesdts()
        call.recuperarPullRequests(nome,login).enqueue(object : Callback<List<PullRequest>> {
            override fun onFailure(call: Call<List<PullRequest>>, t: Throwable) {
                fail(t.message)

            }

            override fun onResponse(call: Call<List<PullRequest>>, response: Response<List<PullRequest>>) {
                if (response.code() == 200) {
                    val resposta = response.body()

                        success(resposta)
                }
            }
        })

        return items
    }

}