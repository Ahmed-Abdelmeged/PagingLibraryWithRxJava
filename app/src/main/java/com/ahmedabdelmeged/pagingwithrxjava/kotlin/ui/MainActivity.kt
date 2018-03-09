package com.ahmedabdelmeged.pagingwithrxjava.kotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ahmedabdelmeged.pagingwithrxjava.R
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.adapter.UserAdapter
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.data.NetworkState
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.data.Status
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_network_state.*

class MainActivity : AppCompatActivity() {

    private lateinit var usersViewModel: UsersViewModel

    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        userAdapter = UserAdapter {
            usersViewModel.retry()
        }
        usersRecyclerView.layoutManager = linearLayoutManager
        usersRecyclerView.adapter = userAdapter
        usersViewModel.userList.observe(this, Observer<PagedList<User>> { userAdapter.submitList(it) })
        usersViewModel.getNetworkState().observe(this, Observer<NetworkState> { userAdapter.setNetworkState(it) })
    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
        usersViewModel.getRefreshState().observe(this, Observer { networkState ->
            if (userAdapter.currentList != null) {
                if (userAdapter.currentList!!.size > 0) {
                    usersSwipeRefreshLayout.isRefreshing = networkState?.status == NetworkState.LOADING.status
                } else {
                    setInitialLoadingState(networkState)
                }
            } else {
                setInitialLoadingState(networkState)
            }
        })
        usersSwipeRefreshLayout.setOnRefreshListener({ usersViewModel.refresh() })
    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState?) {
        //error message
        errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            errorMessageTextView.text = networkState.message
        }

        //loading and retry
        retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE

        usersSwipeRefreshLayout.isEnabled = networkState?.status == Status.SUCCESS
        retryLoadingButton.setOnClickListener { usersViewModel.retry() }
    }

}
