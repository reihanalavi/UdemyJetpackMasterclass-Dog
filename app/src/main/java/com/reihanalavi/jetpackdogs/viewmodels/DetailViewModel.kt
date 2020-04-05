package com.reihanalavi.jetpackdogs.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reihanalavi.jetpackdogs.models.Dog

class DetailViewModel: ViewModel() {

    val dogLiveData =  MutableLiveData<Dog>()

    fun fetch() {

        val dog = Dog("1", "Corgi", "15 years", "breedGroup", "bredFor", "temperament", "")
        dogLiveData.value = dog

    }

}