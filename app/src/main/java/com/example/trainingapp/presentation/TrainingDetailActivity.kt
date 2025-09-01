package com.example.trainingapp.presentation

import androidx.media3.exoplayer.ExoPlayer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import com.example.trainingapp.databinding.TrainingVideoActivityBinding
import javax.inject.Inject


class TrainingDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: TrainingDetailViewModel
    private lateinit var binding: TrainingVideoActivityBinding
    private var exoPlayer: ExoPlayer? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as TrainingApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = TrainingVideoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация ViewModel
        viewModel = ViewModelProvider(this, viewModelFactory)[TrainingDetailViewModel::class.java]

        // Получаем данные тренировки из Intent
        val trainingId = intent.getIntExtra(TRAINING_ID, 0)
        val trainingTitle = intent.getStringExtra(TRAINING_TITLE) ?: ""
        val trainingType = intent.getIntExtra(TRAINING_TYPE, 0)
        val trainingDescription = intent.getStringExtra(TRAINING_DESCRIPTION) ?: ""
        val trainingTime = intent.getStringExtra(TRAINING_TIME) ?: ""

        // Если ID  тренировки и тренировки не переданы  в интент, закрываем активность
        if (!intent.hasExtra(TRAINING_ID) || trainingId == 0) {
            finish()
            return
        }

        // Загружаем данные о видео
        viewModel.loadTrainingInfo(trainingId)


        // Наблюдаем за LiveData для видео
        viewModel.trainingVideo.observe(this, Observer { trainingVideo ->
            // Воспроизведение видео с помощью ExoPlayer (Media3)
            playVideo(trainingVideo.link)
        })

        binding.trainingType.text = setTrainingTypeString(trainingType)
        binding.trainingTitle.text = trainingTitle
        binding.trainingTime.text = "${trainingTime} min"
        binding.trainingDescription.text = trainingDescription

        // Наблюдаем за LiveData для состояний UI
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                Empty -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                    binding.emptyMessage.visibility = View.VISIBLE
                }
                is Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.emptyMessage.visibility = View.GONE
                    binding.errorMessage.text = state.message

                }
                Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                    binding.emptyMessage.visibility = View.GONE
                }
                Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                    binding.emptyMessage.visibility = View.GONE
                }
            }
        })
    }

    // Воспроизведение видео с помощью ExoPlayer (Media3)
    private fun playVideo(link: String) {

        // Инициализируем ExoPlayer (из Media3)
        exoPlayer = ExoPlayer.Builder(this).build()

        // Устанавливаем ссылку на видео в ExoPlayer
        val mediaItem = MediaItem.fromUri(link)
        exoPlayer?.setMediaItem(mediaItem)

        // Подготовка и запуск плеера
        exoPlayer?.prepare()
        exoPlayer?.play()

        // Устанавливаем ExoPlayer в PlayerView для воспроизведения видео
        binding.trainingVideo.player = exoPlayer
    }

    private fun setTrainingTypeString(type: Int): String {
        return when (type) {
            1 -> "тренировка"
            2 -> "эфир"
            3 -> "комплекс"
            else -> ""
        }
    }

    override fun onStop() {
        super.onStop()
        // Освобождаем ресурсы ExoPlayer при остановке Activity
        exoPlayer?.release()
    }


    companion object {
        private const val TRAINING_ID = "training id"
        private const val TRAINING_TIME = "training time"
        private const val TRAINING_TYPE = "training type"
        private const val TRAINING_DESCRIPTION = "training description"
        private const val TRAINING_TITLE = "training title"


        // Создаем Intent для открытия этого экрана
        fun newIntent(
            context: Context,
            id: Int,
            time: String,
            type: Int,
            description: String,
            title: String
        ): Intent {
            val intent = Intent(context, TrainingDetailActivity::class.java)
            intent.putExtra(TRAINING_ID, id)
            intent.putExtra(TRAINING_TIME, time)
            intent.putExtra(TRAINING_TYPE, type)
            intent.putExtra(TRAINING_DESCRIPTION, description)
            intent.putExtra(TRAINING_TITLE, title)
            return intent
        }
    }
}