package com.ahmedabdelmeged.pagingwithrxjava.kotlin.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.api.GithubService
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.data.NetworkState
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.data.datasource.UsersDataSourceFactory
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.model.User
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 */
class UsersViewModel : ViewModel() {

    var userList: LiveData<PagedList<User>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 15

    private val sourceFactory: UsersDataSourceFactory =
        UsersDataSourceFactory(compositeDisposable, GithubService.getService())

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        userList = LivePagedListBuilder(sourceFactory, config).build()

    }

    fun retry() {
        sourceFactory.usersDataSourceLiveData.value!!.retry()
    }

    fun refresh() {
        sourceFactory.usersDataSourceLiveData.value!!.invalidate()
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap(
        sourceFactory.usersDataSourceLiveData) { it.networkState }

    fun getRefreshState(): LiveData<NetworkState> = Transformations.switchMap(
        sourceFactory.usersDataSourceLiveData) { it.initialLoad }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}