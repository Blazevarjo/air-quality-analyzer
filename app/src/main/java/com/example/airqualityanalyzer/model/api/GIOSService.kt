package com.example.airqualityanalyzer.model.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL="https://api.gios.gov.pl/pjp-api/rest/"

object GIOSService {
    private val retrofit by lazy{
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: GIOSRequest by lazy {
        retrofit
            .create(GIOSRequest::class.java)
    }
}