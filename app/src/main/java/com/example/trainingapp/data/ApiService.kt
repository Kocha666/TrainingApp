package com.example.trainingapp.data

import com.example.trainingapp.domain.TrainingItem
import com.example.trainingapp.domain.TrainingVideo
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("get_workouts")
    suspend fun getWorkouts(): List<TrainingItem>

    @GET("get_video")
    suspend fun getVideo(@Query("id") id: Int): TrainingVideo

}