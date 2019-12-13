package com.example.desafioconcrete.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafioconcrete.R
import com.example.desafioconcrete.listener.ScrollListener
import com.example.desafioconcrete.model.Item
import com.example.desafioconcrete.ui.adapter.Adapter_Repositorio
import com.example.desafioconcrete.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy{
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }
    private val adapter by lazy {
        Adapter_Repositorio(this, ArrayList())
    }
    private val layoutManeger by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSwipeOnListener()
        setObservable()
        setAdapter()

    }

    private fun setSwipeOnListener() {
        progress_bar_circular.visibility = View.VISIBLE
        viewModel.initRequest()
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
        recycle_repositorios.layoutManager = layoutManeger
        recycle_repositorios.adapter = adapter
        recycle_repositorios.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        recycle_repositorios.addOnScrollListener(ScrollListener(layoutManeger) {
            progress_bar_circular.visibility = View.VISIBLE
            viewModel.loadMore()
        })
    }
}
