package com.ahmedabdelmeged.pagingwithrxjava.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ahmedabdelmeged.pagingwithrxjava.databinding.ItemNetworkStateBinding;
import com.ahmedabdelmeged.pagingwithrxjava.java.data.NetworkState;
import com.ahmedabdelmeged.pagingwithrxjava.java.data.Status;

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 */
public class NetworkStateViewHolder extends RecyclerView.ViewHolder {

    private final ItemNetworkStateBinding binding;

    private NetworkStateViewHolder(ItemNetworkStateBinding binding, RetryCallback retryCallback) {
        super(binding.getRoot());
        this.binding = binding;
        binding.retryLoadingButton.setOnClickListener(v -> retryCallback.retry());
    }

    public void bindTo(NetworkState networkState) {
        //error message
        binding.errorMessageTextView.setVisibility(networkState.getMessage() != null ? View.VISIBLE : View.GONE);
        if (networkState.getMessage() != null) {
            binding.errorMessageTextView.setText(networkState.getMessage());
        }

        //loading and retry
        binding.retryLoadingButton.setVisibility(networkState.getStatus() == Status.FAILED ? View.VISIBLE : View.GONE);
        binding.loadingProgressBar.setVisibility(networkState.getStatus() == Status.RUNNING ? View.VISIBLE : View.GONE);
    }

    public static NetworkStateViewHolder create(ViewGroup parent, RetryCallback retryCallback) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNetworkStateBinding binding = ItemNetworkStateBinding.inflate(layoutInflater, parent, false);
        return new NetworkStateViewHolder(binding, retryCallback);
    }

}
