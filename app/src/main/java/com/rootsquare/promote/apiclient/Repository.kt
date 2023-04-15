package com.rootsquare.promote.apiclient

import com.rootsquare.promote.model.Demo
import com.rootsquare.promote.model.Getlinks
import retrofit2.Response
import com.rootsquare.promote.model.SendLinks

class Repository {
    suspend fun postLinks(name: String): Response<SendLinks>{
        return ApiHelper.apiInterface.postLinks(name)
    }
    suspend fun getLinks(): Response<Getlinks>{
        return ApiHelper.apiInterface.getLinks()
    }
    suspend fun demo(name: String, job : String): Response<Demo>{
        return ApiHelper.apiInterface.demoApi(name,job)
    }
}