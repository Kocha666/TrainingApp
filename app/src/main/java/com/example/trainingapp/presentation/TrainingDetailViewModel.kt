package com.example.trainingapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.ApiFactory
import com.example.trainingapp.domain.TrainingItem
import com.example.trainingapp.domain.TrainingVideo
import kotlinx.coroutines.launch

class TrainingDetailViewModel: ViewModel() {

    private val _trainingVideo = MutableLiveData<TrainingVideo>()
    val trainingVideo: LiveData<TrainingVideo> = _trainingVideo

    // Метод для получения данных о тренировках
    fun loadTrainingVideo(id: Int) {
        // Логирование перед началом работы
        Log.d("TrainingDetailViewModel", "before coroutine")

        // Запуск корутины для сетевого запроса
        viewModelScope.launch {

            try {
                // Проверка, что корутина срабатывает
                Log.d("TrainingDetailViewModel", "Inside coroutine")

                // Получаем список тренировок из API
                val response = ApiFactory.apiService.getVideo(id)
                Log.d("TrainingDetailViewModel", response.toString())
                _trainingVideo.postValue(response)
            } catch (e: Exception) {
                // Обрабатываем ошибки
                Log.d("TrainingDetailViewModel", e.toString())
            }
        }
    }
}