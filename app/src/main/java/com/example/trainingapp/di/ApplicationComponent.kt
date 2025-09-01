package com.example.trainingapp.di

import android.app.Application
import com.example.trainingapp.presentation.MainActivity
import com.example.trainingapp.presentation.TrainingDetailActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: TrainingDetailActivity)

    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}