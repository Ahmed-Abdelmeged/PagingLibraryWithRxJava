package com.ahmedabdelmeged.pagingwithrxjava.kotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedabdelmeged.pagingwithrxjava.databinding.ItemNetworkStateBinding
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.data.NetworkState
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.data.Status

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 */
class NetworkStateViewHolder(
    private val binding: ItemNetworkStateBinding,
    private val retryCallback: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryLoadingButton.setOnClickListener { retryCallback() }
    }

    companion object {

        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemNetworkStateBinding.inflate(layoutInflater, parent, false)
            return NetworkStateViewHolder(binding, retryCallback)
        }

    }

    fun bindTo(networkState: NetworkState?) {
        // error message
        binding.apply {
            errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
            if (networkState?.message != null) {
                errorMessageTextView.text = networkState.message
            }

            // loading and retry
            retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
            loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
        }
    }

}