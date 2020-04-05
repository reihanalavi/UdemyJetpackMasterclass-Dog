package com.reihanalavi.jetpackdogs.webservice

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.reihanalavi.jetpackdogs.models.Dog
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogService {

    private val BASE_URL = "https://raw.githubusercontent.com/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(DogApi::class.java)

    fun getDogs(): Single<List<Dog>> = api.getDogs()

}