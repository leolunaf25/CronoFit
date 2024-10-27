package com.lunatcoms.cronofit

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.lunatcoms.cronofit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var contador1:Int=10
    private var contador2:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startChronometer()

        binding.pbBarra.progress = 0
    }

    private fun startChronometer(){
        object : CountDownTimer(10000, 1000) {

            override fun onTick(millisUntilFinished: Long) {

                binding.tvChronometer.text = contador1.toString()
                binding.pbBarra.progress = contador2*10
                contador1--
                contador2++
            }

            override fun onFinish() {
                contador1 = 10
                contador2 = 1
                startChronometer()
            }
        }.start()

    }
}