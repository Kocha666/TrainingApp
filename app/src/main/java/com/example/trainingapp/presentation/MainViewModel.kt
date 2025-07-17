package com.example.trainingapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trainingapp.data.ApiFactory
import com.example.trainingapp.domain.TrainingItem
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response


class MainViewModel : ViewModel() {

    // Живые данные для списка тренировок
    private val _trainings = MutableLiveData<List<TrainingItem>>()
    val trainings: LiveData<List<TrainingItem>> = _trainings

    // Метод для получения данных о тренировках
    fun fetchTrainings() {
        // Логирование перед началом работы
        Log.d("MainViewModel", "before coroutine")

        // Запуск корутины для сетевого запроса
        viewModelScope.launch {

            try {
                // Проверка, что корутина срабатывает
                Log.d("MainViewModel", "Inside coroutine")

                // Получаем список тренировок из API
                val response = ApiFactory.apiService.getWorkouts()
                _trainings.postValue(response)
            } catch (e: Exception) {
                // Обрабатываем ошибки
                Log.d("MainViewModel", e.toString())
            }
        }
    }

}