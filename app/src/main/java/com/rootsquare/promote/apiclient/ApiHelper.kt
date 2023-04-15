package com.rootsquare.promote.apiclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiHelper {

  //  val baseUrl = "http://10.0.2.2:5000"
   val baseUrl = "http://192.168.1.38:5000"
  //  val baseUrl = "http://reqres.in"
    private  val retrofit  by lazy{
         Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }

    val apiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }

}
