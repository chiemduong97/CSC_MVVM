package com.example.csc_mvvm.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.example.csc_mvvm.R
import com.example.csc_mvvm.utils.gone
import com.example.csc_mvvm.utils.show


abstract class BaseCollectionFragment<VM: BaseCollectionViewModel>: BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    abstract val viewModel: VM

    private var swipeRefresh: SwipeRefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setBinding(bindingInflater!!.invoke(inflater, container, false))
        val rootView = binding<ViewBinding>().root
        swipeRefresh = rootView.findViewById(R.id.swipe_refresh)
        recyclerView = rootView.findViewById(R.id.recycler_view)
        progressBar = rootView.findViewById(R.id.progress_bar)
        swipeRefresh?.setOnRefreshListener(this@BaseCollectionFragment)
        recyclerView?.run {
            setHasFixedSize(true)
            scrollTo(0, 0)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager: LinearLayoutManager = layoutManager as LinearLayoutManager
                    val totalItemCount: Int = linearLayoutManager.itemCount
                    val lastVisibleItemPosition: Int = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    if (shouldLoadMore() && lastVisibleItemPosition == totalItemCount - 1 && totalItemCount >= 10) {
                        viewModel.loadMore()
                    }
                }
            })
        }
        return rootView
    }

    override fun onRefresh() {
        viewModel.onRefresh()
    }

    protected open fun shouldLoadMore(): Boolean {
        return true
    }

    fun showLoading() {
        swipeRefresh?.isRefreshing = true
    }

    fun hideLoading() {
        swipeRefresh?.apply {
            if (isRefreshing) isRefreshing = false
        }
    }

    fun addLoadMore() {
        progressBar?.show()
    }

    fun removeLoadMore() {
        progressBar?.gone()
    }

}