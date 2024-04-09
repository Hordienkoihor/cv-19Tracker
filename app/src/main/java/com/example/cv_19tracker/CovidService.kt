package com.example.cv_19tracker

import retrofit2.Call
import retrofit2.http.GET

interface CovidService {

    @GET("us/daily.json")
    fun getNetionalData(): Call<List<CovidData>>


    @GET("state/daily.json")
    fun getStateData(): Call<List<CovidData>>
}