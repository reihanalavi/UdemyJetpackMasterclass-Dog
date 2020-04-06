package com.reihanalavi.jetpackdogs.viewmodels

import android.app.Application
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reihanalavi.jetpackdogs.models.Dog
import com.reihanalavi.jetpackdogs.models.DogDatabase
import com.reihanalavi.jetpackdogs.utils.SharedPreferencesHelper
import com.reihanalavi.jetpackdogs.webservice.DogService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class DogViewModel(application: Application) : BaseViewModel(application) {

    private val dogService = DogService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<Dog>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    fun refresh() {
        val updateTime = prefHelper.getUpdateTime()

        if(updateTime != null && updateTime != 0L) {
            if(System.nanoTime() - updateTime < refreshTime) {
                //fetch from local
                fetchFromDatabase()
            } else {

                //fetch from server
                fetchFromRemote()
            }
        }

    }

    fun refreshCache() {
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getDogs()
            dogRetrieved(dogs)
            Toast.makeText(getApplication(), "Dogs retrieved from the local database", LENGTH_SHORT).show()
        }
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
                        storeLocal(responses)
                        Toast.makeText(getApplication(), "Dogs retrieved from the server", LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        error.value = true

                        e.printStackTrace()
                    }

                })
        )
    }

    private fun dogRetrieved(responses: List<Dog>) {
        loading.value = false
        error.value = false

        //set the responses to the list
        dogs.value = responses
    }

    private fun storeLocal(responses: List<Dog>) {
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteDogs()
            val result = dao.insertDogs(*responses.toTypedArray())

            var i = 0
            while (i < responses.size) {
                responses[i].uid = result[i].toInt()
                ++i
            }
            dogRetrieved(responses)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}