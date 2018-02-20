package com.ahmedabdelmeged.pagingwithrxjava.java.api;

import com.ahmedabdelmeged.pagingwithrxjava.java.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ahmed Abd-Elmeged on 2/13/2018.
 */
public interface GithubService {

    /**
     * Get github users
     *
     * @param userId  the id of the user to get the more users after it
     * @param perPage number of users ber page
     * @return list of github users
     */
    @GET("/users")
    Single<List<User>> getUsers(@Query("since") long userId, @Query("per_page") int perPage);

    static GithubService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GithubService.class);
    }

}
