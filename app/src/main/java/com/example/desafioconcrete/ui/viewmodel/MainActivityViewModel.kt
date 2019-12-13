package com.example.desafioconcrete.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafioconcrete.model.Item
import com.example.desafioconcrete.retrofit.webclient.BaseRepository

class MainActivityViewModel : ViewModel(){

    private val repository = BaseRepository()
    private var repositoriesLiveData = MutableLiveData<ArrayList<Item>>()
    private val collectionAll = MutableLiveData<ArrayList<Item>>()

    var page = 1

    fun initRequest(paging:Int = page) {
        repository.getRepositories(paging, {
            repositoriesLiveData.value = it?.items
        }, {})
    }

    fun loadMore() {
        page++
        repository.getRepositories(page, {
            if (it != null) {
                repositoriesLiveData.value?.addAll(it.items)
                collectionAll.value = it.items
            } }, {})
    }

    fun getLiveData() = repositoriesLiveData

    fun getCollectionAll() = collectionAll

}