package com.example.trainingapp.presentation

import android.app.Application
import com.example.trainingapp.di.DaggerApplicationComponent

class TrainingApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}