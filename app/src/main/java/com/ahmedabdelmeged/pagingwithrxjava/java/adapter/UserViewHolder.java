package com.ahmedabdelmeged.pagingwithrxjava.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ahmedabdelmeged.pagingwithrxjava.R;
import com.ahmedabdelmeged.pagingwithrxjava.databinding.ItemUserBinding;
import com.ahmedabdelmeged.pagingwithrxjava.java.model.User;
import com.bumptech.glide.Glide;


/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 */
public class UserViewHolder extends RecyclerView.ViewHolder {

    private final ItemUserBinding binding;

    private UserViewHolder(ItemUserBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindTo(User user) {
        binding.UserName.setText(user.getLogin());
        Glide.with(itemView.getContext())
                .load(user.getAvatarUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.UserAvatar);
        binding.siteAdminIcon.setVisibility(user.isSiteAdmin() ? View.VISIBLE : View.GONE);
    }

    public static UserViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemUserBinding binding = ItemUserBinding.inflate(layoutInflater, parent, false);
        return new UserViewHolder(binding);
    }

}
