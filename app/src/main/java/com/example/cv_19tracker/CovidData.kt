package com.example.cv_19tracker

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CovidData(
     val dateChacked: Date,
     val positiveIncrease: Int,
     val negativeIncrease: Int,
     val deathIncrease: Int,
     val state: String,
)
