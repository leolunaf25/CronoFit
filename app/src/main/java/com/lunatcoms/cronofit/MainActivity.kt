package com.lunatcoms.cronofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lunatcoms.cronofit.databinding.ActivityMainBinding
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var createRing: CreateRing


    private var totalTime:Long = 30000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRing = binding.pbBarraDefinitive

        binding.pbBarra3.setProgressWithAnimation(100f,30000L)

        setProgressWithAnimation(100f,30000L)

    }


    private fun setProgressWithAnimation(progress: Float, duration: Long = 30000L) {
        val currentProgress = createRing.currentProgress
        ValueAnimator.ofFloat(currentProgress.toFloat(), progress).apply {
            this.duration = duration
            this.interpolator = LinearInterpolator()
            this.startDelay = startDelay
            addUpdateListener { animation ->
                createRing.currentProgress = (animation.animatedValue as Float)
                createRing.invalidate()
            }
            start()
        }
    }

}