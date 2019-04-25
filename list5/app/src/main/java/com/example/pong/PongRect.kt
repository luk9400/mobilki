package com.example.pong

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class PongRect(private val left: Boolean, var x: Float, var y: Float) {
    val rectHeight = 500f
    val rectWidth = 50f

    fun draw(canvas: Canvas) {
        val rect = if (left) {
            RectF(x, y, x + rectWidth, y + rectHeight)
        } else {
            RectF(x - rectWidth, y, x + rectWidth, y + rectHeight)
        }

        canvas.drawRect(rect, Paint().also { it.setARGB(255, 255,255, 255) })
    }

    fun moveRect(newY: Float) {
        y = (newY - rectHeight / 2)
    }
}