package com.ahmedabdelmeged.pagingwithrxjava.java.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.ahmedabdelmeged.pagingwithrxjava.java.data.datasource.UsersDataSource;
import com.ahmedabdelmeged.pagingwithrxjava.java.data.datasource.UsersDataSourceFactory;
import com.ahmedabdelmeged.pagingwithrxjava.java.model.User;
import com.ahmedabdelmeged.pagingwithrxjava.java.data.NetworkState;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ahmed Abd-Elmeged on 2/13/2018.
 */
public class UsersViewModel extends ViewModel {

    LiveData<PagedList<User>> userList;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final int pageSize = 15;

    private UsersDataSourceFactory usersDataSourceFactory;

    public UsersViewModel() {
        usersDataSourceFactory = new UsersDataSourceFactory(compositeDisposable);
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build();

        userList = new LivePagedListBuilder<>(usersDataSourceFactory, config).build();
    }

    public void retry() {
        usersDataSourceFactory.getUsersDataSourceLiveData().getValue().retry();
    }

    public void refresh() {
        usersDataSourceFactory.getUsersDataSourceLiveData().getValue().invalidate();
    }

    public LiveData<NetworkState> getNetworkState() {
        return Transformations.switchMap(usersDataSourceFactory.getUsersDataSourceLiveData(), UsersDataSource::getNetworkState);
    }

    public LiveData<NetworkState> getRefreshState() {
        return Transformations.switchMap(usersDataSourceFactory.getUsersDataSourceLiveData(), UsersDataSource::getInitialLoad);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

}
