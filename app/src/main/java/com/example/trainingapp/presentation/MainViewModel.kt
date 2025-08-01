package com.example.trainingapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trainingapp.domain.entity.TrainingItem
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.TrainingListRepositoryImpl
import com.example.trainingapp.domain.entity.UIState
import com.example.trainingapp.domain.usecases.GetWorkoutsUseCase
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {


    private val repository = TrainingListRepositoryImpl()

    private val getWorkoutsUseCase = GetWorkoutsUseCase(repository)

    // Живые данные для списка тренировок
    private val _trainings = MutableLiveData<List<TrainingItem>>()
    val trainings: LiveData<List<TrainingItem>> = _trainings

    // Живые данные для состояний загрузки
    private val _state = MutableLiveData<UIState>()
    val state: LiveData<UIState> = _state

    // Метод для получения данных о тренировках
    fun fetchTrainings() {

        // Показываем индикатор загрузки
        _state.value = UIState.Loading

        // Запуск корутины
        viewModelScope.launch {

            try {
                // Получаем список тренировок из API
                val response = getWorkoutsUseCase.getWorkouts()

                // Если данные есть, то показываем их, иначе - пустой список
                if (response.isNotEmpty()) {
                    _trainings.postValue(response)
                    _state.value = UIState.Success
                } else {
                    _state.value = UIState.Empty  // Если данных нет, показываем пустой экран
                }
            } catch (e: Exception) {
                _state.value = UIState.Error(
                    e.message ?: "Error"
                )  // В случае ошибки показываем сообщение об ошибке
            }
        }
    }

}