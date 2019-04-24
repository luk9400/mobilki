package com.example.pong


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
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
        thread.setRunning(true)
        thread.start()
    }

    fun update() {

        ballX += dx
        ballY += dy

        if (ballX <= 0 || ballX + SIZE >= width) {
            dx = -dx
        }
        if (ballY <= 0 || ballY + SIZE >= height) {
            dy = -dy
        }

    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if (canvas == null) return

        val red = Paint()
        red.setARGB(255, 255, 0, 0)
        canvas.drawOval(RectF(ballX, ballY, ballX + SIZE, ballY + SIZE), red)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        ballX = event.x.toFloat() - (SIZE / 2)
        ballY = event.y.toFloat() - (SIZE / 2)

        if (ballX <= 0) ballX = abs(dx)
        if (ballX + SIZE >= width) ballX = width.toFloat() - SIZE - abs(dx)
        if (ballY <= 0) ballY = abs(dy)
        if (ballY + SIZE >= height) ballY = height.toFloat() - SIZE - abs(dy)

        return true

        return super.onTouchEvent(event)
    }
}