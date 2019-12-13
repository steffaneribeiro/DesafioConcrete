package com.example.desafioconcrete.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioconcrete.ui.adapter.Adapter_Repositorio
import com.example.desafioconcrete.R
import com.example.desafioconcrete.listener.ScrollListener
import com.example.desafioconcrete.retrofit.helper.RepositoriesConfig
import com.example.desafioconcrete.model.Item
import com.example.desafioconcrete.model.Resultado
import com.example.desafioconcrete.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pull_request.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy{
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    var repositorios :ArrayList<Item> = ArrayList()

    var layoutManager = LinearLayoutManager(this)
    lateinit var adapter: Adapter_Repositorio


    // Variables for pagination
    var page_number = 1
    var isLoading = false
    var pastVisibleItems = 0; var visibleItemCount = 0; var totalItemCount = 0; var previous_total = 0





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSwipeOnListener()
        setObservable()
        setAdapter()


       /*
        recuperarRepositorios()*/


    }

    /*fun condigurarRecyclerView(){

        recycle_repositorios.setHasFixedSize(true)
        recycle_repositorios.layoutManager = layoutManager
        recycle_repositorios.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
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


        val youtubeService = RepositoriesConfig().createRetrofit()
        youtubeService.recuperarRepositorios(page = page_number).enqueue(object : Callback<Resultado> {
            override fun onFailure(call: Call<Resultado>, t: Throwable) {}
            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {

                val resultado = response.body()
                repositorios = resultado?.items as ArrayList<Item>
                recycle_repositorios.adapter = Adapter_Repositorio(this@MainActivity, repositorios)
                progress_bar_circular.visibility = View.GONE
                condigurarRecyclerView()
            }

        })

    }


    fun performPagaination(){


        isLoading = true
        progress_bar_circular.visibility = View.VISIBLE
        val repositoryService = RepositoriesConfig().createRetrofit()
        repositoryService.recuperarRepositorios( page = page_number

        ).enqueue(object : Callback<Resultado> {
            override fun onFailure(call: Call<Resultado>, t: Throwable) {}
            override fun onResponse(call: Call<Resultado>, response: Response<Resultado>) {

                if(response.message() == "OK"){

                    var resultado = response.body()
                    var essamerda = resultado?.items as ArrayList<Item>


                    for( i in essamerda){

                        repositorios.add(i)

                    }
                    Adapter_Repositorio(
                        this@MainActivity
                    ).addRepositorios(repositorios)
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

*/
    private fun setSwipeOnListener() {
        progress_bar_circular.visibility = View.VISIBLE
        viewModel.initRequest( 1)



    }

    private fun setObservable() {

        viewModel.getCollectionAll().observe(this, Observer {
            progress_bar_circular.visibility = View.GONE
            updateList(it)
        })

        viewModel.getLiveData().observe(this, Observer {
            adapter.pessoaList!!.clear()
            updateList(it)

        })


    }


    private fun updateList(it: ArrayList<Item>?) {
        it?.let {
            adapter.pessoaList!!.addAll(it)
            adapter.notifyDataSetChanged()
            viewModel.getCollectionAll().value = null


        }
    }

    private fun setAdapter() {
        val adapter = Adapter_Repositorio(this, ArrayList())
        var layoutManeger = LinearLayoutManager(this)
        val recyclerViewvar = recycle_repositorios
        recyclerViewvar.layoutManager = layoutManeger
        recyclerViewvar.adapter = adapter
        recycle_repositorios.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recycle_repositorios.addOnScrollListener(ScrollListener(layoutManeger) {
            progress_bar_circular.visibility = View.VISIBLE
            viewModel.loadMore()

        })

        this.adapter = adapter
    }



}
