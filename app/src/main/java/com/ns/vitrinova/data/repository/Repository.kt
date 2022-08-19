package com.ns.vitrinova.data.repository

import com.ns.vitrinova.data.model.DiscoverModel
import com.ns.vitrinova.data.remote.ServiceApi
import com.ns.vitrinova.utils.Resource
import com.ns.vitrinova.utils.Utils.safeApiCall
import javax.inject.Inject

class Repository @Inject constructor(private val serviceApi: ServiceApi) {

    suspend fun getContent(): Resource<DiscoverModel> {
        return safeApiCall(call = { serviceApi.getContent() })
    }
}