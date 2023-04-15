package com.rootsquare.promote.linkhit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rootsquare.promote.apiclient.Repository
import com.rootsquare.promote.model.Getlinks
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LinkHitViewModel(private val repository: Repository) : ViewModel() {
    val getLinksMutable: MutableLiveData<Getlinks> = MutableLiveData()

    fun getLinks(){
        GlobalScope.launch{
            val response = repository.getLinks()
            getLinksMutable.postValue(response.body())
            Log.d("get links == ", response.body()?.size.toString())
        }
    }

}