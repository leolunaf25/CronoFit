package com.lunatcoms.cronofit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

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
}