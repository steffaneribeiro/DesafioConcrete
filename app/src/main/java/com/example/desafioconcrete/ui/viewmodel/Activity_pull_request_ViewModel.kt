package com.example.desafioconcrete.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafioconcrete.model.PullRequest
import com.example.desafioconcrete.retrofit.webclient.BasePullRequest

class Activity_pull_request_ViewModel:ViewModel() {

    private val repository = BasePullRequest()
    private var repositoriesLiveData = MutableLiveData<ArrayList<PullRequest>>()

    fun initRequest(nome:String,login:String) {
        repository.getPullRequest(nome,login, {
            repositoriesLiveData.value = it as ArrayList<PullRequest>?
        }, {})
    }

    fun getLiveData() = repositoriesLiveData

}