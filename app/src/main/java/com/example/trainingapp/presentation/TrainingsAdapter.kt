package com.example.trainingapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingapp.databinding.TrainingItemBinding
import com.example.trainingapp.domain.TrainingItem

class TrainingsAdapter(
    private var trainingList: List<TrainingItem>,
    private val onItemClick: (TrainingItem) -> Unit  // Лямбда-функция для обработки кликов
) :
    RecyclerView.Adapter<TrainingsAdapter.TrainingItemViewHolder>() {

    private var filteredList = trainingList.toMutableList()
    private var savedType: Int = 0
    private var savedSearch: String = ""

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrainingItemViewHolder {
        val binding = TrainingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrainingItemViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: TrainingItemViewHolder,
        position: Int
    ) {
        val trainingItem = filteredList[position]
        holder.binding.trainingTitle.text = trainingItem.title
        holder.binding.trainingType.text = setTrainingTypeString(trainingItem.type)
        holder.binding.trainingTime.text = "${trainingItem.duration} min"
        holder.binding.trainingDescription.text = trainingItem.description

        holder.binding.root.setOnClickListener {
            onItemClick(trainingItem)
        }


    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    // Метод для обновления данных в адаптере
    fun updateData(newList: List<TrainingItem>) {
        trainingList = newList
        filteredList = newList.toMutableList()
        notifyDataSetChanged()  // Уведомляем адаптер об изменениях
    }

    private fun setTrainingTypeString(type: Int): String {
        return when (type) {
            1 -> "тренировка"
            2 -> "эфир"
            3 -> "комплекс"
            else -> ""
        }
    }

    // Фильтрация списка по типу тренировки
    fun filterByType(selectedType: Int) {
        savedType = selectedType
        filter()
    }

    // Фильтрация списка по названию тренировки
    fun filterByName(query: String) {
        savedSearch = query
        filter();
    }

    private fun filter(){

        var partiallyFilteredList = when (savedType) {
            0 -> trainingList.toMutableList()  // Все тренировки
            1 -> trainingList.filter { it.type == 1 }.toMutableList()  // Тренировка
            2 -> trainingList.filter { it.type == 2 }.toMutableList()  // Эфир
            3 -> trainingList.filter { it.type == 3 }.toMutableList()  // Комплекс
            else -> trainingList.toMutableList()
        }
        filteredList = partiallyFilteredList.filter {
            it.title.contains(savedSearch, ignoreCase = true)  // Фильтруем по названию (нечувствительно к регистру)
        }.toMutableList()
        notifyDataSetChanged()
    }


    inner class TrainingItemViewHolder(val binding: TrainingItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}