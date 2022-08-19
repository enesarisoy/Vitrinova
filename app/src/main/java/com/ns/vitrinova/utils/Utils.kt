package com.ns.vitrinova.utils

import retrofit2.Response

object Utils {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val myResp = call.invoke()
            if (myResp.isSuccessful) {
                Resource.success(myResp.body()!!)
            } else {
                Resource.error(myResp.errorBody()?.string() ?: "Something goes wrong")
            }
        } catch (e: Exception) {
            Resource.error(e.message.toString())
        }

    }
}