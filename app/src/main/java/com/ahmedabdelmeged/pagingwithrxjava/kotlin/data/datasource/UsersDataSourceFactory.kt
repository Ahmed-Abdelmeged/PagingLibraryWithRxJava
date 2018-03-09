package com.ahmedabdelmeged.pagingwithrxjava.kotlin.data.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.api.GithubService
import com.ahmedabdelmeged.pagingwithrxjava.kotlin.model.User
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 *
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class UsersDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                             private val githubService: GithubService)
    : DataSource.Factory<Long, User> {

    val usersDataSourceLiveData = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Long, User> {
        val usersDataSource = UsersDataSource(githubService, compositeDisposable)
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }

}
