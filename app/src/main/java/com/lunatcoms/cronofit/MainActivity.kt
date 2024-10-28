package com.lunatcoms.cronofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lunatcoms.cronofit.databinding.ActivityMainBinding
import android.os.Handler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.animation.ObjectAnimator
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var totalTime:Long = 30000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        binding.pbBarra3.max = 100

        val animator = ObjectAnimator.ofInt(binding.pbBarra3,"progress",50)
        animator.duration = 2000
        animator.start()


    }

    private fun initUI() {
        starChronometer()
        startChronometerAndCounter()
    }

    private fun starChronometer(){
        CoroutineScope(Dispatchers.Main).launch {
            var progress = 0
            while (true) {
                delay(totalTime / 100L)
                progress += 1
                binding.pbBarra.progress = progress
                binding.pbBarraHorizontal.progress = progress

                if (progress >= 100) {
                    progress = 0
                }
            }
        }
    }

    private fun startChronometerAndCounter() {
        CoroutineScope(Dispatchers.Main).launch {
            val startTime = System.currentTimeMillis()

            while (true) {
                val elapsedTime = System.currentTimeMillis() - startTime

                // Calcular el progreso en función del tiempo transcurrido
                val progress = ((elapsedTime % totalTime) / totalTime.toDouble()) * 100
                //binding.pbBarra2.progress = progress.toInt()
                binding.pbBarraHorizontal2.progress = progress.toInt()

                // Calcular el tiempo restante en segundos para el cronómetro
                val timeCurrent = (totalTime - (elapsedTime % totalTime)) / 1000
                binding.tvChronometer2.text = (timeCurrent+1).toString()

                delay(17L)
            }
        }
    }
}