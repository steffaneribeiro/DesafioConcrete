package com.example.desafioconcrete.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioconcrete.API.PullRequestsService
import com.example.desafioconcrete.API.RepositorioService
import com.example.desafioconcrete.R
import com.example.desafioconcrete.adapter.Adapter_PullRequests
import com.example.desafioconcrete.adapter.Adapter_Repositorio
import com.example.desafioconcrete.helper.PullRequestsConfig
import com.example.desafioconcrete.helper.RecyclerViewTouchListener
import com.example.desafioconcrete.model.Item
import com.example.desafioconcrete.model.PullRequest
import com.example.desafioconcrete.model.Resultado
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pull_request.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import kotlin.properties.Delegates

class Activity_pull_request : AppCompatActivity() {

    var pullRequest :ArrayList<PullRequest> = ArrayList()
    lateinit var retrofit: Retrofit

    var idRepositorio by Delegates.notNull<Int>()
    var login:String = " "
    var nome:String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)

        var bundle = intent.extras
        if (bundle != null){

            idRepositorio = bundle.getInt("IdRepositorio")
            login = bundle.getString("login").toString()
            nome = bundle.getString("name").toString()

        }

        progress_bar_circular_pull_request.visibility = View.VISIBLE
        retrofit = PullRequestsConfig().getRetrofit_PullRequesdts()
        recuperarRepositorios()

    }

    fun condigurarRecyclerView(){


        recycler_pull_request.setHasFixedSize(true)
        recycler_pull_request.layoutManager = LinearLayoutManager(this)

        recycler_pull_request.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))




        recycler_pull_request!!.addOnItemTouchListener(
            RecyclerViewTouchListener(
                applicationContext,
                recycler_pull_request!!,
                object : RecyclerViewTouchListener.ClickListener {
                    override fun onLongClick(view: View?, position: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onClick(view: View, position: Int) {

                        var pull = pullRequest.get(position)

                        val openURL = Intent(android.content.Intent.ACTION_VIEW)
                        openURL.data = Uri.parse(pull.html_url)
                        startActivity(openURL)


                    }
                })
        )

    }

    fun recuperarRepositorios(){

        val pullRequestService = retrofit.create(PullRequestsService::class.java)
        pullRequestService.recuperarPullRequests(nome,login).enqueue(object : Callback<List<PullRequest>> {
            override fun onFailure(call: Call<List<PullRequest>>, t: Throwable) {

                Log.d("resultado", "FALHOU")

            }

            override fun onResponse(call: Call<List<PullRequest>>, response: Response<List<PullRequest>>) {



                Log.d("resultado", response.toString())

                var resultado = response.body()
                pullRequest = resultado as ArrayList<PullRequest>
                recycler_pull_request.adapter = Adapter_PullRequests(this@Activity_pull_request, pullRequest)
                progress_bar_circular_pull_request.visibility = View.GONE
                condigurarRecyclerView()
                Toast.makeText(this@Activity_pull_request,"First page is loaded",Toast.LENGTH_SHORT).show()
            }

        })

    }

}