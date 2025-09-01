package com.example.trainingapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trainingapp.domain.entity.TrainingVideo
import com.example.trainingapp.domain.usecases.GetVideoUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrainingDetailViewModel @Inject constructor(
    private val getVideoUseCase: GetVideoUseCase
) : ViewModel() {

    private val _trainingVideo = MutableLiveData<TrainingVideo>()
    val trainingVideo: LiveData<TrainingVideo> = _trainingVideo

    // Живые данные для состояний загрузки
    private val _state = MutableLiveData<UIState>()
    val state: LiveData<UIState>
        get() = _state

    // Метод для получения данных о тренировке
    fun loadTrainingInfo(id: Int) {
        // Запуск корутины для сетевого запроса
        viewModelScope.launch {
            // Показываем индикатор загрузки
            _state.value = Loading
            try {
                // Получаем тренирвку по айди из API
                val videoTraining = getVideoUseCase.getVideo(id)
                if (videoTraining != null) {
                    //пушим данные в лайв дату
                    _trainingVideo.postValue(videoTraining)
                    //обновляем состояние
                    _state.value = Success
                } else {
                    _state.value = Empty
                }
            } catch (e: Exception) {
                _state.value = Error(
                    e.message ?: "Error"
                )
            }
        }
    }
}