package com.example.trainingapp.presentation

sealed class UIState

object Loading : UIState()
object Success : UIState()
object Empty : UIState()
class Error(val message: String) : UIState()