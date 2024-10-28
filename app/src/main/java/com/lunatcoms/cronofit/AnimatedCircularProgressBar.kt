package com.lunatcoms.cronofit

import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.animation.ValueAnimator
import android.view.View

class AnimatedCircularProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progressAnimator: ValueAnimator? = null
    private var currentProgress: Float = 0f
    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 80f // Grosor de la línea del progreso
    }

    init {
        paint.color = resources.getColor(android.R.color.holo_blue_light) // Color del progreso
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = Math.min(centerX, centerY) - paint.strokeWidth / 2

        // Dibuja el arco del progreso
        canvas.drawArc(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            -90f, // Comienza desde la parte superior
            (currentProgress * 360 / 100), // Progreso en grados
            false,
            paint
        )
    }

    fun setProgressWithAnimation(
        progress: Float,
        duration: Long = 1000L, // Duración por defecto
        interpolator: TimeInterpolator = LinearInterpolator(), // Interpolador por defecto
        startDelay: Long = 0L // Delay por defecto
    ) {
        progressAnimator?.cancel()

        // Configura el ValueAnimator para animar el progreso
        progressAnimator = ValueAnimator.ofFloat(currentProgress, progress).apply {
            this.duration = duration
            this.interpolator = interpolator
            this.startDelay = startDelay
            addUpdateListener { animation ->
                currentProgress = animation.animatedValue as Float
                invalidate() // Redibuja la vista para mostrar el nuevo progreso
            }
            start() // Inicia la animación
        }
    }
}
