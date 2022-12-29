package com.ahmedabdelmeged.pagingwithrxjava.java.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmedabdelmeged.pagingwithrxjava.core.BaseActivity;
import com.ahmedabdelmeged.pagingwithrxjava.databinding.ActivityMainBinding;
import com.ahmedabdelmeged.pagingwithrxjava.java.adapter.RetryCallback;
import com.ahmedabdelmeged.pagingwithrxjava.java.adapter.UserAdapter;
import com.ahmedabdelmeged.pagingwithrxjava.java.data.NetworkState;
import com.ahmedabdelmeged.pagingwithrxjava.java.data.Status;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements RetryCallback {

    private UsersViewModel usersViewModel;

    private UserAdapter userAdapter;

    @NonNull
    @Override
    public ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        initAdapter();
        initSwipeToRefresh();
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        userAdapter = new UserAdapter(this);
        getBinding().usersRecyclerView.setLayoutManager(linearLayoutManager);
        getBinding().usersRecyclerView.setAdapter(userAdapter);
        usersViewModel.userList.observe(this, userAdapter::submitList);
        usersViewModel.getNetworkState().observe(this, userAdapter::setNetworkState);
    }

    /**
     * Init swipe to refresh and enable pull to refresh only when there are items in the adapter
     */
    private void initSwipeToRefresh() {
        usersViewModel.getRefreshState().observe(this, networkState -> {
            if (networkState != null) {
                if (userAdapter.getCurrentList() != null) {
                    if (userAdapter.getCurrentList().size() > 0) {
                        getBinding().usersSwipeRefreshLayout.setRefreshing(
                                networkState.getStatus() == NetworkState.LOADING.getStatus());
                    } else {
                        setInitialLoadingState(networkState);
                    }
                } else {
                    setInitialLoadingState(networkState);
                }
            }
        });
        getBinding().usersSwipeRefreshLayout.setOnRefreshListener(() -> usersViewModel.refresh());
    }

    /**
     * Show the current network state for the first load when the user list
     * in the adapter is empty and disable swipe to scroll at the first loading
     *
     * @param networkState the new network state
     */
    private void setInitialLoadingState(NetworkState networkState) {
        //error message
        getBinding().containerLoading.errorMessageTextView.setVisibility(networkState.getMessage() != null ? View.VISIBLE : View.GONE);
        if (networkState.getMessage() != null) {
            getBinding().containerLoading.errorMessageTextView.setText(networkState.getMessage());
        }

        //loading and retry
        getBinding().containerLoading.retryLoadingButton.setVisibility(networkState.getStatus() == Status.FAILED ? View.VISIBLE : View.GONE);
        getBinding().containerLoading.loadingProgressBar.setVisibility(networkState.getStatus() == Status.RUNNING ? View.VISIBLE : View.GONE);
        getBinding().containerLoading.retryLoadingButton.setOnClickListener(v -> usersViewModel.retry());

        getBinding().usersSwipeRefreshLayout.setEnabled(networkState.getStatus() == Status.SUCCESS);
    }

    @Override
    public void retry() {
        usersViewModel.retry();
    }

}
