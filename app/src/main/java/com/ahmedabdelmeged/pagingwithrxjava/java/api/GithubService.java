package com.ahmedabdelmeged.pagingwithrxjava.java.api;

import static java.util.Collections.emptySet;

import com.ahmedabdelmeged.pagingwithrxjava.PagingApp;
import com.ahmedabdelmeged.pagingwithrxjava.java.model.User;
import com.chuckerteam.chucker.api.ChuckerCollector;
import com.chuckerteam.chucker.api.ChuckerInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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

        HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
        mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient mClient = new OkHttpClient.Builder()
                .addInterceptor(mLoggingInterceptor)
                .addInterceptor(new ChuckerInterceptor.Builder(PagingApp.getContextExt())
                        .collector(new ChuckerCollector(PagingApp.getContextExt()))
                        .maxContentLength(250000L)
                        .redactHeaders(emptySet())
                        .alwaysReadResponseBody(false)
                        .build())
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build();
        return retrofit.create(GithubService.class);
    }

}
