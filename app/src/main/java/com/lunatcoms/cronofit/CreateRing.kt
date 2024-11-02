package com.lunatcoms.cronofit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColor

class CreateRing @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var currentProgress: Float = 0f
    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 80f // Grosor de la línea del progreso
    }
    private val paint2: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 80f // Grosor de la línea del progreso
    }

    init {
        paint.color = resources.getColor(android.R.color.holo_blue_light) // Color del progreso
        paint2.color = resources.getColor(android.R.color.darker_gray) // Color del progreso

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = Math.min(centerX, centerY) - paint.strokeWidth / 2

        canvas.drawArc(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            -90f, // Comienza desde la parte superior
            (100f * 360 / 100), // Progreso en grados
            false,
            paint2
        )

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
}