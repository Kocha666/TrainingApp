package com.example.trainingapp.domain


sealed class UIState {
    object Loading : UIState()
    object Success : UIState()
    object Empty : UIState()
    data class Error(val message: String) : UIState()
}