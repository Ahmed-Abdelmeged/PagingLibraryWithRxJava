package com.ahmedabdelmeged.pagingwithrxjava.java.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedabdelmeged.pagingwithrxjava.GlideApp;
import com.ahmedabdelmeged.pagingwithrxjava.R;
import com.ahmedabdelmeged.pagingwithrxjava.java.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 */
public class UserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.UserName)
    TextView userName;

    @BindView(R.id.UserAvatar)
    ImageView userAvatar;

    @BindView(R.id.siteAdminIcon)
    ImageView siteAdminIcon;

    private UserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindTo(User user) {
        userName.setText(user.getLogin());
        GlideApp.with(itemView.getContext())
                .load(user.getAvatarUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(userAvatar);
        siteAdminIcon.setVisibility(user.isSiteAdmin() ? View.VISIBLE : View.GONE);
    }

    public static UserViewHolder create(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

}
