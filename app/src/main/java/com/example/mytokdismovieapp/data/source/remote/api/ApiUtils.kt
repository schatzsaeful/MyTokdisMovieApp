package com.example.mytokdismovieapp.data.source.remote.api

import com.example.mytokdismovieapp.data.source.remote.api.RetrofitClient.client

object ApiUtils {
    @JvmStatic
    val service: BaseApiService?
        get() = client?.create(BaseApiService::class.java)
}