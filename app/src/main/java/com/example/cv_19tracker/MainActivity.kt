package com.example.cv_19tracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cv_19tracker.ui.theme.Cv19TrackerTheme
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
private const val BASE_URL = "https://covidtracking.com/api/v1/"
private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    private lateinit var perStateDailyData: Map<String, List<CovidData>>
    private lateinit var nationalDailyData: List<CovidData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm;ss").create()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            val covidService = retrofit.create(CovidService::class.java)
            //Fetch the national data
            covidService.getNetionalData().enqueue(object : Callback<List<CovidData>>{
                override fun onResponse(p0: Call<List<CovidData>>, p1: Response<List<CovidData>>) {
                    Log.i(TAG, "onFailure $p1")
                    val nationalData = p1.body()
                    if (nationalData == null){
                        Log.w(TAG, "Did not recieve a valid responce body")
                        return
                    }
                    nationalDailyData = nationalData.reversed()
                    Log.i(TAG, "Update graph with national data")
                }

                override fun onFailure(p0: Call<List<CovidData>>, p1: Throwable) {
                    Log.e(TAG, "onFailure $p1")
                }

            })
            covidService.getStateData().enqueue(object : Callback<List<CovidData>>{
                override fun onResponse(p0: Call<List<CovidData>>, p1: Response<List<CovidData>>) {
                    Log.i(TAG, "onFailure $p1")
                    val stateData = p1.body()
                    if (stateData == null){
                        Log.w(TAG, "Did not recieve a valid responce body")
                        return
                    }
                    perStateDailyData = stateData.reversed().groupBy { it.state }
                    Log.i(TAG, "Update spinner with state data")
                }

                override fun onFailure(p0: Call<List<CovidData>>, p1: Throwable) {
                    Log.e(TAG, "onFailure $p1")
                }
        })
    }
}}

