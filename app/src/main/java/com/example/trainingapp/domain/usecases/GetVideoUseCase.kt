package com.example.trainingapp.domain.usecases

import com.example.trainingapp.domain.entity.TrainingVideo
import com.example.trainingapp.domain.repository.TrainingListRepository
import javax.inject.Inject

class GetVideoUseCase @Inject constructor(
    private val trainingListRepository: TrainingListRepository
){
    suspend fun getVideo(workoutId: Int): TrainingVideo {
        return trainingListRepository.getVideo(workoutId)
    }
}