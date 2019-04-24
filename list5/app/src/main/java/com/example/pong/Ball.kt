package com.example.pong

import android.graphics.Canvas
import android.graphics.Paint

class Ball(var x:Float, var y: Float) {
    private val size = 50f
    private var dx = 15f
    private var dy = 15f
    private lateinit var gameView: GameView

    fun draw(canvas: Canvas) {
        canvas.drawOval(x, y, x + size, y + size, Paint().also { it.setARGB(255, 255, 255, 255) })
    }

    fun changeDirection(left: Float, right: Float) {
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
}