package com.example.pong


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Math.abs

class GameView(context: Context, attributeSet: AttributeSet) : SurfaceView(context, attributeSet),
    SurfaceHolder.Callback {

    private val thread: GameThread

    private var ballX = 0f
    private var ballY = 0f
    private var dx = 5f
    private var dy = 5f
    private val SIZE = 300f

    lateinit var leftRect: PongRect
    lateinit var rightRect: PongRect
    lateinit var ball: Ball

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

        thread.setRunning(true)
        thread.start()
    }

    fun update() {
        ball.move()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if (canvas == null) return

        leftRect.draw(canvas)
        rightRect.draw(canvas)
        ball.draw(canvas)
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