package com.example.trainingapp.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.trainingapp.R
import com.example.trainingapp.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TrainingsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as TrainingApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализируем ViewModel
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        //  Запускаем загрузку данных
        viewModel.fetchTrainings()

        // Настройка RecyclerView
        adapter = TrainingsAdapter(emptyList()) {
            Toast.makeText(this, "Вы выбрали: ${it.title} и ${it.id}", Toast.LENGTH_SHORT).show()
            //запускаем второй экран
            val intent = TrainingDetailActivity.newIntent(
                this,
                it.id,
                it.duration,
                it.type,
                it.description.toString(),
                it.title
            )
            startActivity(intent)
        }

        // Устанавливаем адаптер в RecyclerView
        binding.recyclerViewTrainingsCards.adapter = adapter

        viewModel.state.observe(this, Observer {
            when (it) {
                is Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewTrainingsCards.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.emptyMessage.visibility = View.GONE
                    binding.errorMessage.text = it.message
                }
                Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerViewTrainingsCards.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                    binding.emptyMessage.visibility = View.GONE
                }
                Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewTrainingsCards.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                    binding.emptyMessage.visibility = View.GONE
                }
                Empty -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewTrainingsCards.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                    binding.emptyMessage.visibility = View.VISIBLE
                }
            }
        })

        viewModel.trainings.observe(this, Observer { trainings ->
            // Когда данные обновляются, обновляем адаптер
            if (trainings != null && trainings.isNotEmpty()) {
                adapter.updateData(trainings)
            } else {
                Log.d("MainActivity", "No trainings available")
            }
        })

        // Устанавливаем слушатель для выбора типа тренировки в RadioGroup
        setupRadioGroupListener()
        // Настройка SearchView для поиска по названию тренировки
        setupSearchViewListener()

    }

    private fun setupRadioGroupListener() {
        binding.radioGroupTrainings.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_all -> {
                    // Фильтруем по всем тренировкам
                    adapter.filterByType(0)
                }

                R.id.radio_workout -> {
                    // Фильтруем по типу "Тренировка"
                    adapter.filterByType(1)
                }

                R.id.radio_stream -> {
                    // Фильтруем по типу "Эфир"
                    adapter.filterByType(2)
                }

                R.id.radio_complex -> {
                    // Фильтруем по типу "Комплекс"
                    adapter.filterByType(3)
                }
            }
        }
    }

    private fun setupSearchViewListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    // Фильтруем по названию тренировки
                    adapter.filterByName(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    // Фильтруем по названию тренировки
                    adapter.filterByName(it)
                }
                return true
            }
        })
    }

}