package com.example.pong

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.random.Random

class Ball(private val initX:Float, private val initY: Float) {
    val size = 50f
    var dx = 15f
    var dy = 15f
    var x = initX
    var y = initY
    private lateinit var gameView: GameView

    fun draw(canvas: Canvas) {
        canvas.drawOval(x, y, x + size, y + size, Paint().also { it.setARGB(255, 255, 255, 255) })
    }

    private fun changeDirection(left: Float, right: Float) {
        if (x <= left || x + size >= right) {
            dx = -dx
        }
        if (y <= 0f || y + size >= gameView.height.toFloat()) {
            dy = -dy
        }
    }

    fun move() {
        x += dx
        y += dy
        changeDirection(0f, gameView.width.toFloat())
    }

    fun setGameView(gameView: GameView) {
        this.gameView = gameView
    }

    fun reset() {
        x = initX
        y = initY
        dx = -(15 + 5 * Random.nextFloat()) * Math.pow((-1).toDouble(), Random.nextInt(3).toDouble()).toFloat()
        dy = (15 + 5 * Random.nextFloat()) * Math.pow((-1).toDouble(), Random.nextInt(3).toDouble()).toFloat()
    }
}