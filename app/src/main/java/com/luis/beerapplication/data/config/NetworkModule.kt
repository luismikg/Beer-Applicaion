package com.luis.beerapplication.data.config

import com.google.gson.GsonBuilder
import com.luis.beerapplication.domain.WebService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    val webService by lazy {
        Retrofit.Builder()
            .baseUrl(Web.URL_BASE)
            .addConverterFactory( //JSON TO OBJECT
                GsonConverterFactory.create(GsonBuilder().create())
            )
            .build()
            .create(WebService::class.java) //Create the retrofit object implementing the interface "WebService"
    }
}