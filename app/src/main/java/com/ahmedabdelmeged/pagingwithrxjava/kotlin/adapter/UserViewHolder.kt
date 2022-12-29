package com.ahmedabdelmeged.pagingwithrxjava.kotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedabdelmeged.pagingwithrxjava.R
import com.ahmedabdelmeged.pagingwithrxjava.databinding.ItemUserBinding
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.model.User
import com.bumptech.glide.Glide

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 */
class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun create(parent: ViewGroup): UserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
            return UserViewHolder(binding)
        }

    }

    fun bindTo(user: User?) {
        binding.UserName.text = user?.login
        Glide.with(itemView.context)
            .load(user?.avatarUrl)
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.UserAvatar)
        binding.siteAdminIcon.visibility = if (user!!.siteAdmin) View.VISIBLE else View.GONE
    }

}