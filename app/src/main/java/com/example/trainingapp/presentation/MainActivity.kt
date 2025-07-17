package com.example.trainingapp.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trainingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TrainingsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("MainViewModel", "before viewmodel create")
        // Инициализируем ViewModel
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Запускаем загрузку данных
        viewModel.fetchTrainings()

        // Настроить RecyclerView
        adapter = TrainingsAdapter(emptyList()) { // Начинаем с пустого списка

            Toast.makeText(this, "Вы выбрали: ${it.title} и ${it.id}", Toast.LENGTH_SHORT).show()

            //запускаем второй экран
            val intent = TrainingDetailActivity.newIntent(this, it.id)
            startActivity(intent)

        }

        // Устанавливаем адаптер в RecyclerView
        binding.recyclerViewTrainingsCards.adapter = adapter

        viewModel.trainings.observe(this, Observer { trainings ->
            // Когда данные обновляются, обновляем адаптер
            if (trainings != null && trainings.isNotEmpty()) {
                adapter.updateData(trainings)
            } else {
                // Логика для случая, если список тренировок пуст
                Log.d("MainActivity", "No trainings available")
            }
        })

    }
}