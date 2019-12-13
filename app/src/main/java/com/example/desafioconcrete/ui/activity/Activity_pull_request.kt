package com.example.desafioconcrete.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafioconcrete.R
import com.example.desafioconcrete.listener.ScrollListener
import com.example.desafioconcrete.model.Item
import com.example.desafioconcrete.ui.adapter.Adapter_PullRequests
import com.example.desafioconcrete.retrofit.helper.PullRequestsConfig
import com.example.desafioconcrete.model.PullRequest
import com.example.desafioconcrete.ui.adapter.Adapter_Repositorio
import com.example.desafioconcrete.ui.viewmodel.Activity_pull_request_ViewModel
import com.example.desafioconcrete.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pull_request.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class Activity_pull_request : AppCompatActivity() {

    private val viewModel by lazy{
        ViewModelProviders.of(this).get(Activity_pull_request_ViewModel::class.java)
    }

    var pullRequest :ArrayList<PullRequest>? = ArrayList()

    lateinit var repositorio: Item
    lateinit var adapter: Adapter_PullRequests

    var idRepositorio by Delegates.notNull<Int>()
    var login:String = " "
    var nome:String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)

        var bundle = intent.extras
        if (intent.hasExtra("repositorio")){

            repositorio = bundle?.getSerializable("repositorio") as Item
            idRepositorio = repositorio.id!!
            login = repositorio.owner!!.login
            nome = repositorio.name

        }

        setSwipeOnListener()
        setObservable()
        setAdapter()

        //progress_bar_circular_pull_request.visibility = View.VISIBLE
        //recuperarRepositorios()

    }

   /* fun condigurarRecyclerView() {


        recycler_pull_request.setHasFixedSize(true)
        recycler_pull_request.layoutManager = LinearLayoutManager(this)

        recycler_pull_request.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

    }

    fun recuperarRepositorios(){

        val pullRequestService = PullRequestsConfig().createRetrofitPullRequesdts()
        pullRequestService.recuperarPullRequests(nome,login).enqueue(object : Callback<List<PullRequest>> {
            override fun onFailure(call: Call<List<PullRequest>>, t: Throwable) {}

            override fun onResponse(call: Call<List<PullRequest>>, response: Response<List<PullRequest>>) {

                val resultado = response.body()
                if (resultado.isNullOrEmpty()){
                    setContentView(R.layout.error)
                }
                else{
                    pullRequest = resultado as ArrayList<PullRequest>
                    recycler_pull_request.adapter =
                        Adapter_PullRequests(
                            this@Activity_pull_request,
                            pullRequest!!
                        )
                    progress_bar_circular_pull_request.visibility = View.GONE
                    condigurarRecyclerView()
                }

            }

        })

    }*/

    private fun setSwipeOnListener() {
        progress_bar_circular_pull_request.visibility = View.VISIBLE
        viewModel.initRequest(nome,login)





    }
    private fun updateList(it: ArrayList<PullRequest>?) {
        it?.let {
            adapter.pessoaList!!.addAll(it)
            adapter.notifyDataSetChanged()
            viewModel.getCollectionAll().value = null


        }
    }

    private fun setObservable() {

        viewModel.getCollectionAll().observe(this, Observer {
            progress_bar_circular_pull_request.visibility = View.GONE
            updateList(it)
        })

        viewModel.getLiveData().observe(this, Observer {
            if (it.isNullOrEmpty()){
                setContentView(R.layout.error)
            }
            else{
                adapter.pessoaList!!.clear()
                updateList(it)
            }

        })

    }

    private fun setAdapter() {
        val adapter = Adapter_PullRequests(this, ArrayList())
        var layoutManeger = LinearLayoutManager(this)
        val recyclerViewvar = recycler_pull_request
        recyclerViewvar.layoutManager = layoutManeger
        recyclerViewvar.adapter = adapter
        recycler_pull_request.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        this.adapter = adapter
    }

}