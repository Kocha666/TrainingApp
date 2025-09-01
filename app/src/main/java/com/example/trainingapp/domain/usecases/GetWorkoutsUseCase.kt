package com.example.trainingapp.domain.usecases

import com.example.trainingapp.domain.entity.TrainingItem
import com.example.trainingapp.domain.repository.TrainingListRepository
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val trainingListRepository: TrainingListRepository
){

    suspend fun getWorkouts(): List<TrainingItem> {
        return trainingListRepository.getWorkouts()
    }
}