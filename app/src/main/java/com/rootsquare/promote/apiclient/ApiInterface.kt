package com.rootsquare.promote.apiclient

// Retrofit interface

import com.rootsquare.promote.model.Demo
import com.rootsquare.promote.model.SendLinks
import com.rootsquare.promote.model.Getlinks
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("/getPromoteLink")
    suspend fun getLinks() : Response<Getlinks>

    @FormUrlEncoded
    @POST("/sendPromoteLink")
    suspend fun postLinks(
        @Field("name") name:String
    ): Response<SendLinks>


    @POST("api/users")
    suspend fun demoApi(
        @Field("name") name:String,
        @Field("job") job:String
    ): Response<Demo>
}
