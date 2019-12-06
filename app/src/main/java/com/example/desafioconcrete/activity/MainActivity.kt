package com.example.desafioconcrete.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioconcrete.API.RepositorioService
import com.example.desafioconcrete.adapter.Adapter_Repositorio
import com.example.desafioconcrete.R
import com.example.desafioconcrete.helper.RecyclerViewTouchListener
import com.example.desafioconcrete.helper.RepositoriesConfig
import com.example.desafioconcrete.model.Item
import com.example.desafioconcrete.model.Resultado
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    var repositorios :ArrayList<Item> = ArrayList()
    lateinit var retrofit: Retrofit

    var layoutManager = LinearLayoutManager(this)
    lateinit var adapter:Adapter_Repositorio


    // Variables for pagination
    var page_number = 1
    var isLoading = false
    var pastVisibleItems = 0; var visibleItemCount = 0; var totalItemCount = 0; var previous_total = 0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress_bar_circular.visibility = View.VISIBLE
        retrofit = RepositoriesConfig().getRetrofit()
        recuperarRepositorios()
    }

    fun condigurarRecyclerView(){

        recycle_repositorios.setHasFixedSize(true)
        recycle_repositorios.layoutManager = layoutManager

        recycle_repositorios.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        recycle_repositorios!!.addOnItemTouchListener(
            RecyclerViewTouchListener(
                applicationContext,
                recycle_repositorios!!,
                object : RecyclerViewTouchListener.ClickListener {
                    override fun onLongClick(view: View?, position: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onClick(view: View, position: Int) {

                        var repositorio = repositorios.get(position)
                        var idRepositorio = repositorio.id
                        var full_name = repositorio.full_name
                        var name = repositorio.name
                        var login = repositorio.owner!!.login


                        val intent = Intent(this@MainActivity, Activity_pull_request::class.java)
                        intent.putExtra("IdRepositorio",idRepositorio)
                        intent.putExtra("full_name" , full_name)
                        intent.putExtra("name" , name)
                        intent.putExtra("login" , login)
                        startActivity(intent)

                    }
                })
        )

        recycle_repositorios.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    visibleItemCount = recyclerView.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (isLoading) {
                        if (totalItemCount > previous_total) {

                            pastVisibleItems = totalItemCount
                        }
                    }
                    if (!isLoading && (visibleItemCount+pastVisibleItems)>=totalItemCount && pastVisibleItems >=0) {
                        page_number++
                        performPagaination()

                    }
                }
            }

        })

    }

    fun recuperarRepositorios(){


        val youtubeService = retrofit.create(RepositorioService::class.java)
        youtubeService.recuperarVideos(

            q = "language:Java",sort = "stars", page = page_number

        ).enqueue(object : Callback<Resultado> {
            override fun onFailure(call: Call<Resultado>, t: Throwable) {

                Log.d("resultado", "FALHOU")


            }

            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {

                Log.d("resultado", response.toString())

                var resultado = response.body()
                repositorios = resultado?.items as ArrayList<Item>
                recycle_repositorios.adapter = Adapter_Repositorio(this@MainActivity, repositorios)
                progress_bar_circular.visibility = View.GONE
                condigurarRecyclerView()
               Toast.makeText(this@MainActivity,"First page is loaded",Toast.LENGTH_SHORT).show()


            }

        })

    }


    fun performPagaination(){


        isLoading = true
        progress_bar_circular.visibility = View.VISIBLE
        val youtubeService = retrofit.create(RepositorioService::class.java)
        youtubeService.recuperarVideos(

            q = "language:Java",sort = "stars", page = page_number

        ).enqueue(object : Callback<Resultado> {
            override fun onFailure(call: Call<Resultado>, t: Throwable) {

                Log.d("resultado", "FALHOU")


            }

            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {

                if(response.message() == "OK"){

                    var resultado = response.body()
                    var essamerda = resultado?.items as ArrayList<Item>


                    for( i in essamerda){

                        repositorios.add(i)

                    }
                    Adapter_Repositorio(this@MainActivity).addRepositorios(repositorios)
                    condigurarRecyclerView()
                    Toast.makeText(this@MainActivity,"Page $page_number is loaded...",Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(this@MainActivity,"No more repositories...",Toast.LENGTH_SHORT).show()
                }
                isLoading = false
                progress_bar_circular.visibility = View.GONE
            }

        })

    }



}
