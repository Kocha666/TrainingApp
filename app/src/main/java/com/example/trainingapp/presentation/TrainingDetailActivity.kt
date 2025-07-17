package com.example.trainingapp.presentation

import androidx.media3.exoplayer.ExoPlayer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import com.example.trainingapp.databinding.TrainingVideoActivityBinding


class TrainingDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: TrainingDetailViewModel
    private lateinit var binding: TrainingVideoActivityBinding

    // Объявляем переменную для ExoPlayer (из Media3)
    private var exoPlayer: ExoPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TrainingVideoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем ID тренировки из Intent
        val videoId = intent.getIntExtra(EXTRA_TRAINING_ID, 0)

        // Если ID тренировки не передан или не был передан в интент, закрываем активность
        if (!intent.hasExtra(EXTRA_TRAINING_ID) || videoId == 0) {
            finish()
            return
        }

        // Инициализация ViewModel
        viewModel = ViewModelProvider(this)[TrainingDetailViewModel::class.java]
        // Загружаем данные о видео
        viewModel.loadTrainingVideo(videoId)
        // Наблюдаем за LiveData для видео
        viewModel.trainingVideo.observe(this, Observer { trainingVideo ->
            // Обновляем UI с данными о видео
            binding.trainingTime.text = "${trainingVideo.duration} min"
            // Воспроизведение видео с помощью ExoPlayer (Media3)
            playVideo(trainingVideo.link)

        })


    }

    // Воспроизведение видео с помощью ExoPlayer (Media3)
    private fun playVideo(videoUrl: String) {
        // Инициализируем ExoPlayer (из Media3)
        exoPlayer = ExoPlayer.Builder(this).build()

        // Устанавливаем ссылку на видео в ExoPlayer
        val mediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer?.setMediaItem(mediaItem)

        // Подготовка и запуск плеера
        exoPlayer?.prepare()
        exoPlayer?.play()

        // Устанавливаем ExoPlayer в PlayerView для воспроизведения видео
        binding.trainingVideo.player = exoPlayer
    }

    override fun onStop() {
        super.onStop()
        // Освобождаем ресурсы ExoPlayer при остановке Activity
        exoPlayer?.release()
    }


    companion object {
        private const val EXTRA_TRAINING_ID = "training id"

        // Создаем Intent для открытия этого экрана
        fun newIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, TrainingDetailActivity::class.java)
            intent.putExtra(EXTRA_TRAINING_ID, id)
            return intent
        }
    }
}