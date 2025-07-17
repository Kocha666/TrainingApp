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
        val trainingItem = trainingList[position]
        holder.binding.trainingTitle.text = trainingItem.title
        holder.binding.trainingType.text = setTrainingTypeString(trainingItem.type)
        holder.binding.trainingTime.text = "${trainingItem.duration} min"
        holder.binding.trainingDescription.text = trainingItem.description

        holder.binding.root.setOnClickListener {
            onItemClick(trainingItem)
        }



    }

    override fun getItemCount(): Int {
        return trainingList.size
    }

    // Метод для обновления данных в адаптере
    fun updateData(newList: List<TrainingItem>) {
        trainingList = newList
        notifyDataSetChanged()  // Уведомляем адаптер об изменениях
    }

    fun setTrainingTypeString(type: Int): String{
        return when(type){
            1 -> "тренировка"
            2 -> "эфир"
            3 -> "комплекс"
            else -> ""
        }
    }

    inner class TrainingItemViewHolder(val binding: TrainingItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}