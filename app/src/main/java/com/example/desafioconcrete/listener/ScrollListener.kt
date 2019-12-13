package com.example.desafioconcrete.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollListener(
    val layoutManager: LinearLayoutManager,
    val loadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    var isLoading = false
    var pastVisibleItems = 0; var visibleItemCount = 0; var totalItemCount = 0; var previous_total = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        if (dy > 0) {
            visibleItemCount = layoutManager.childCount
            totalItemCount = layoutManager.itemCount
            pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

            if (isLoading) {
                if (totalItemCount > previous_total) {

                    pastVisibleItems = totalItemCount
                }
            }
            if (!isLoading && (visibleItemCount+pastVisibleItems)>=totalItemCount && pastVisibleItems >=0) {
                isLoading = false
                loadMore()

            }
        }
        super.onScrolled(recyclerView, dx, dy)
    }
}

