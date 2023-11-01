package com.luis.beerapplication.domain

import com.luis.beerapplication.data.config.Web
import com.luis.beerapplication.data.model.Bar
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("${Web.URL_BASE}beers?")
    suspend fun getData(@Query("page") page: String): Bar
}