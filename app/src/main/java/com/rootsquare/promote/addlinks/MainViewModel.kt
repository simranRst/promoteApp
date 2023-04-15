package com.rootsquare.promote.addlinks

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rootsquare.promote.apiclient.Repository
import com.rootsquare.promote.model.Demo
import com.rootsquare.promote.model.Getlinks
import retrofit2.Response
import com.rootsquare.promote.model.SendLinks
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class  MainViewModel(private val repository: Repository) : ViewModel() {
    val myRes: MutableLiveData<Response<SendLinks>> = MutableLiveData()
    val demo: MutableLiveData<Response<Demo>> = MutableLiveData()
    val getLinksMutable: MutableLiveData<Getlinks> = MutableLiveData()

    val regex = ("((http|https)://)(www.)?"
            + "[a-zA-Z0-9@:%._\\+~#?&//=]"
            + "{2,256}\\.[a-z]"
            + "{2,6}\\b([-a-zA-Z0-9@:%"
            + "._\\+~#?&//=]*)")

    val p: Pattern = Pattern.compile(regex)

    fun postLinks(name: String){
        GlobalScope.launch{
        val response = repository.postLinks(name = name)
            myRes.postValue(response)
            Log.d("demo == ", response.body()?.name.toString())
            Log.d("name == ",name)
        }
    }
    fun getLinks(){
        GlobalScope.launch{
        val response = repository.getLinks()
            getLinksMutable.postValue(response.body())
            Log.d("get links == ", response.body()?.size.toString())


        }
    }
    fun demo(){
        GlobalScope.launch{
        val response = repository.demo("name", "job")
            demo.postValue(response)
            Log.d("demo == ",response.toString())
        }

    }
}