package com.ahmedabdelmeged.pagingwithrxjava.kotlin.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedabdelmeged.pagingwithrxjava.core.BaseActivity
import com.ahmedabdelmeged.pagingwithrxjava.databinding.ActivityMainBinding
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.adapter.UserAdapter
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.data.NetworkState
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.data.Status

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var userAdapter: UserAdapter

    private val usersViewModel: UsersViewModel by lazy {
        ViewModelProviders.of(this)[UsersViewModel::class.java]
    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        userAdapter = UserAdapter {
            usersViewModel.retry()
        }
        binding.usersRecyclerView.layoutManager = linearLayoutManager
        binding.usersRecyclerView.adapter = userAdapter
        usersViewModel.userList.observe(this) { userAdapter.submitList(it) }
        usersViewModel.getNetworkState().observe(this) { userAdapter.setNetworkState(it) }
    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private fun initSwipeToRefresh() {
        usersViewModel.getRefreshState().observe(this) { networkState ->
            if (userAdapter.currentList != null) {
                if (userAdapter.currentList!!.size > 0) {
                    binding.usersSwipeRefreshLayout.isRefreshing =
                        networkState?.status == NetworkState.LOADING.status
                } else {
                    setInitialLoadingState(networkState)
                }
            } else {
                setInitialLoadingState(networkState)
            }
        }
        binding.usersSwipeRefreshLayout.setOnRefreshListener { usersViewModel.refresh() }
    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private fun setInitialLoadingState(networkState: NetworkState?) {
        //error message
        binding.containerLoading.errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            binding.containerLoading.errorMessageTextView.text = networkState.message
        }

        //loading and retry
        binding.containerLoading.retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        binding.containerLoading.loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE

        binding.usersSwipeRefreshLayout.isEnabled = networkState?.status == Status.SUCCESS
        binding.containerLoading.retryLoadingButton.setOnClickListener { usersViewModel.retry() }
    }

}
