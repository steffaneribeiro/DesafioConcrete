package com.example.desafioconcrete.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafioconcrete.R
import com.example.desafioconcrete.model.Item
import com.example.desafioconcrete.ui.adapter.Adapter_PullRequests
import com.example.desafioconcrete.ui.viewmodel.Activity_pull_request_ViewModel
import kotlinx.android.synthetic.main.activity_pull_request.*

class Activity_pull_request : AppCompatActivity() {

    private val viewModel by lazy{
        ViewModelProviders.of(this).get(Activity_pull_request_ViewModel::class.java)
    }
    private val adapter by lazy{
        Adapter_PullRequests(this, ArrayList())
    }
    private  val layoutManeger by lazy{
        LinearLayoutManager(this)
    }

    lateinit var repositorio: Item
    lateinit var login:String
    lateinit var nome:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)

        val bundle = intent.extras
        if (intent.hasExtra("repositorio")){

            repositorio = bundle?.getSerializable("repositorio") as Item
            login = repositorio.owner!!.login
            nome = repositorio.name

        }

        setSwipeOnListener()
        setObservable()
        setAdapter()
    }

    private fun setSwipeOnListener() {
        progress_bar_circular_pull_request.visibility = View.VISIBLE
        viewModel.initRequest(nome,login)
    }

    private fun setObservable() {

        viewModel.getLiveData().observe(this, Observer {
            progress_bar_circular_pull_request.visibility = View.GONE
            if (it.isNullOrEmpty()){
                setContentView(R.layout.error)
            }
            else{
                it.let {
                    adapter.pessoaList.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun setAdapter() {
        recycler_pull_request.layoutManager = layoutManeger
        recycler_pull_request.adapter = adapter
        recycler_pull_request.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
    }

}