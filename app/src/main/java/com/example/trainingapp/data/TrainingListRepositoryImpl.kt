package com.example.trainingapp.data

import com.example.trainingapp.domain.entity.TrainingItem
import com.example.trainingapp.domain.entity.TrainingVideo
import com.example.trainingapp.domain.repository.TrainingListRepository
import javax.inject.Inject

class TrainingListRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TrainingListRepository {

    override suspend fun getVideo(workoutId: Int): TrainingVideo {
        // Получаем объект TrainingVideo из API
        val trainingVideo = apiService.getVideo(workoutId)

        // Добавляем BASE_URL к link
        return trainingVideo.copy(link = ApiFactory.BASE_URL + trainingVideo.link.trimStart('/'))

    }

    override suspend fun getWorkouts(): List<TrainingItem>  {
        return apiService.getWorkouts()
    }


}