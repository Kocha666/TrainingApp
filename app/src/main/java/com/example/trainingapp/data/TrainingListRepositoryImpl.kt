package com.example.trainingapp.data


import com.example.trainingapp.domain.entity.TrainingItem
import com.example.trainingapp.domain.entity.TrainingVideo
import com.example.trainingapp.domain.repository.TrainingListRepository

class TrainingListRepositoryImpl() : TrainingListRepository {

    private val apiService = ApiFactory.apiService //не знаю можно ли сделать так
    // или необходимо передавать в конструкторе репозитория


    override suspend fun getVideo(workoutId: Int): TrainingVideo {
        return apiService.getVideo(workoutId)
    }

    override suspend fun getWorkouts(): List<TrainingItem>  {
        return apiService.getWorkouts()
    }
}