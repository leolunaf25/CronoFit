package com.lunatcoms.cronofit

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.lunatcoms.cronofit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var createRing: CreateRing


    private var totalTime: Long = 5000
    private var progressTarget: Float = 100F
    private var isPlay: Boolean = true

    private var startTime: Long = 0L
    private var isPaused: Boolean = false
    private var job: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnInit.setOnClickListener {
            initUI()
        }

        binding.pause.setOnClickListener { onPause() }
        binding.play.setOnClickListener { onResume() }


    }

    private fun initUI() {
        createRing = binding.pbBarraDefinitive
        setProgress(progressTarget, totalTime)
        startChronometer()
    }

    private fun setProgress(progress: Float, duration: Long = 30000L) {
        val currentProgress = createRing.currentProgress
        ValueAnimator.ofFloat(currentProgress, progress).apply {
            this.duration = duration
            repeatMode = ValueAnimator.RESTART
            this.interpolator = LinearInterpolator()
            this.startDelay = startDelay
            addUpdateListener { animation ->
                createRing.currentProgress = animation.animatedValue as Float
                createRing.invalidate()
                //Log.i("Progress",createRing.currentProgress.toString())
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    start() // Reinicia el animador para un nuevo ciclo
                }
            })
            start()
        }
    }

    private fun startChronometer() {
        job = CoroutineScope(Dispatchers.Main).launch {
            val startTime = System.currentTimeMillis()

            while (true) {

                val elapsedTime = System.currentTimeMillis() - startTime

                // Calcular el progreso en función del tiempo transcurrido
                val progress = ((elapsedTime % totalTime) / totalTime.toDouble()) * 100
                binding.pbBarraHorizontal.progress = progress.toInt()

                // Calcular el tiempo restante en segundos para el cronómetro
                val timeCurrent = ((totalTime - (elapsedTime % totalTime)) / 1000) + 1
                binding.tvChronometer.text = (timeCurrent).toString()

                delay(17L)


            }
        }
    }

    override fun onPause() {
        super.onPause()
        isPlay = false
        isPaused = true
        job?.cancel() // Cancela la corrutina al pausar
    }

    override fun onResume() {
        super.onResume()
        if (isPaused) {
            isPlay = true
            isPaused = false
            startTime = System.currentTimeMillis() -
                    (binding.pbBarraHorizontal.progress * totalTime / 100) // Ajusta startTime para el tiempo transcurrido
            startChronometer() // Reanuda la corrutina desde el progreso actual
        }
    }

}