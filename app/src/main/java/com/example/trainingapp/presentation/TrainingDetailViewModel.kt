package com.example.trainingapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.data.ApiFactory
import com.example.trainingapp.domain.TrainingVideo
import com.example.trainingapp.domain.UIState
import kotlinx.coroutines.launch

class TrainingDetailViewModel : ViewModel() {

    private val _trainingVideo = MutableLiveData<TrainingVideo>()
    val trainingVideo: LiveData<TrainingVideo> = _trainingVideo

    // Живые данные для состояний загрузки
    private val _state = MutableLiveData<UIState>()
    val state: LiveData<UIState> = _state

    // Метод для получения данных о тренировке
    fun loadTrainingInfo(id: Int) {
        // Запуск корутины для сетевого запроса
        viewModelScope.launch {
            // Показываем индикатор загрузки
            _state.value = UIState.Loading
            try {
                // Получаем тренирвку по айди из API
                val videoTraining = ApiFactory.apiService.getVideo(id)
                if (videoTraining != null) {
                    //пушим данные в лайв дату
                    _trainingVideo.postValue(videoTraining)

                    //обновляем состояние
                    _state.value = UIState.Success
                } else {
                    _state.value = UIState.Empty
                }
            } catch (e: Exception) {
                _state.value = UIState.Error(
                    e.message ?: "Error"
                )
            }
        }
    }
}