package com.example.trainingapp.domain

data class TrainingItem (
    val id: Int,
    val title: String,
    val description: String?,
    val type: Int,
    val duration: String
)