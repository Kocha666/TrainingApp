package com.example.trainingapp.di

import androidx.lifecycle.ViewModel
import com.example.trainingapp.presentation.MainViewModel
import com.example.trainingapp.presentation.TrainingDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrainingDetailViewModel::class)
    fun bindTrainingDetailViewModel(viewModel: TrainingDetailViewModel): ViewModel
}