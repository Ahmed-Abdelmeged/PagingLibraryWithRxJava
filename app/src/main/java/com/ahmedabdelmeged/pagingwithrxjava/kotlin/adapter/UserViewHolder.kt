package com.ahmedabdelmeged.pagingwithrxjava.kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahmedabdelmeged.pagingwithrxjava.GlideApp
import com.ahmedabdelmeged.pagingwithrxjava.R
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.model.User
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 */
class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(user: User?) {
        itemView.UserName.text = user?.login
        GlideApp.with(itemView.context)
                .load(user?.avatarUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(itemView.UserAvatar)
        itemView.siteAdminIcon.visibility = if (user!!.siteAdmin) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_user, parent, false)
            return UserViewHolder(view)
        }
    }

}