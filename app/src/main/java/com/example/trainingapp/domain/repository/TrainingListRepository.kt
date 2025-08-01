package com.example.trainingapp.domain.repository


import com.example.trainingapp.domain.entity.TrainingItem
import com.example.trainingapp.domain.entity.TrainingVideo

interface TrainingListRepository {

    suspend fun getVideo(workoutId: Int): TrainingVideo

    suspend fun getWorkouts(): List<TrainingItem>

}