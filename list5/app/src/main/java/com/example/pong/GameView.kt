package com.example.pong

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Math.abs

class GameView(context: Context, attributeSet: AttributeSet) : SurfaceView(context, attributeSet),
    SurfaceHolder.Callback {

    private val sharedPref = context.getSharedPreferences("highScore", Context.MODE_PRIVATE)
    private val thread: GameThread
    lateinit var leftRect: PongRect
    lateinit var rightRect: PongRect
    lateinit var ball: Ball
    lateinit var game: Game

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        thread.setRunning(false)
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        leftRect = PongRect(true, 10f, height / 2f)
        rightRect = PongRect(false, width.toFloat() - 10f, height / 2f)

        ball = Ball(width / 2f, height / 2f)
        ball.setGameView(this)

        game = Game(leftRect, rightRect, ball, sharedPref.getInt("highScore", 0))

        thread.setRunning(true)
        thread.start()
    }

    fun update() {
        game.lookForPoints()
        if (game.score()) {
            sharedPref.edit().putInt("highScore", game.highScore).apply()
            ball.reset()
        }
        ball.move()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if (canvas == null) return

        leftRect.draw(canvas)
        rightRect.draw(canvas)
        ball.draw(canvas)
        printScore(canvas)
    }

    private fun printScore(canvas: Canvas?) {
        if (canvas == null) return

        val textPaint = TextPaint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 300f
        textPaint.alpha = 100
        textPaint.textAlign = Paint.Align.CENTER

        val x = canvas.width / 2f
        val y = canvas.height / 2f
        canvas.drawText("${game.leftPoints} : ${game.rightPoints}", x, y, textPaint)
        textPaint.textSize = 100f
        canvas.drawText("${game.highScore}", x, y - 200f, textPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        for (i in 0 until event.pointerCount) {
            if (event.getX(i) < width/2) {
                leftRect.moveRect(event.getY(i))
            } else {
                rightRect.moveRect(event.getY(i))
            }
        }
        return true
    }
}