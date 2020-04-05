package com.reihanalavi.jetpackdogs.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reihanalavi.jetpackdogs.models.Dog
import com.reihanalavi.jetpackdogs.webservice.DogService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DogViewModel : ViewModel() {

    private val dogService = DogService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<Dog>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true
        error.value = false
        disposable.addAll(
            dogService
                .getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Dog>>() {

                    override fun onSuccess(responses: List<Dog>) {
                        loading.value = false
                        error.value = false

                        //set the responses to the list
                        dogs.value = responses
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        error.value = true

                        e.printStackTrace()
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}