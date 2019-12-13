package com.example.desafioconcrete.listener

import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollListener(
    val layoutManager: LinearLayoutManager,
    val loadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    var isScrolling = false
    private var currentItems: Int = 0
    private var totalItems: Int = 0
    private var scrollOutItems: Int = 0


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            isScrolling = true

        }
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {


        currentItems = layoutManager.childCount
        totalItems = layoutManager.itemCount
        scrollOutItems = layoutManager.findFirstVisibleItemPosition()


        if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
            //data fetch
            isScrolling = false
            loadMore()
        }
        super.onScrolled(recyclerView, dx, dy)
    }


}