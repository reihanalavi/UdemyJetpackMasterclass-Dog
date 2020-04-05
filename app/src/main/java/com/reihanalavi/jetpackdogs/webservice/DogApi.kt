package com.reihanalavi.jetpackdogs.webservice

import com.reihanalavi.jetpackdogs.models.Dog
import io.reactivex.Single
import retrofit2.http.GET

interface DogApi {

    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs(): Single<List<Dog>>

}