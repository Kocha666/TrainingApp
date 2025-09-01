package com.example.trainingapp.di

import com.example.trainingapp.data.ApiFactory
import com.example.trainingapp.data.ApiService
import com.example.trainingapp.data.TrainingListRepositoryImpl
import com.example.trainingapp.domain.repository.TrainingListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindTrainingListRepository(impl: TrainingListRepositoryImpl): TrainingListRepository

    companion object{

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService{
            return ApiFactory.apiService
        }
    }
}