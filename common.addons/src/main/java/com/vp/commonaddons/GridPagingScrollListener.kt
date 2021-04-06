package com.vp.commonaddons

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridPagingScrollListener(private val layoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    interface LoadMoreItemsListener {
        fun loadMoreItems(page: Int)
    }

    private var loadMoreItemsListener: LoadMoreItemsListener? = null
    private var isLastPage = false
    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (shouldLoadNextPage()) {
            loadMoreItemsListener?.loadMoreItems(getNextPageNumber())
        }
    }

    fun setLoadMoreItemsListener(loadMoreItemsListener: LoadMoreItemsListener?) {
        this.loadMoreItemsListener = loadMoreItemsListener
    }

    fun markLoading(isLoading: Boolean) {
        this.isLoading = isLoading
    }

    fun markLastPage(isLastPage: Boolean) {
        this.isLastPage = isLastPage
    }

    private fun shouldLoadNextPage(): Boolean {
        return isNotLoadingInProgress() && userScrollsToNextPage() && isNotFirstPage() && hasNextPage()
    }

    private fun userScrollsToNextPage(): Boolean {
        return layoutManager.childCount + layoutManager.findFirstVisibleItemPosition() >= layoutManager.itemCount
    }

    private fun isNotFirstPage(): Boolean {
        return layoutManager.findFirstVisibleItemPosition() >= 0 && layoutManager.itemCount >= PAGE_SIZE
    }

    private fun hasNextPage(): Boolean {
        return !isLastPage
    }

    private fun isNotLoadingInProgress(): Boolean {
        return !isLoading
    }

    private fun getNextPageNumber(): Int {
        return layoutManager.itemCount / PAGE_SIZE + 1
    }
}