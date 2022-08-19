package com.ns.vitrinova.data.remote

import com.ns.vitrinova.data.model.DiscoverModel
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {

    @GET("discover")
    suspend fun getContent(): Response<DiscoverModel>
}