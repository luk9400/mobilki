package com.example.pong

class Game(private val leftRect: PongRect, private val rightRect: PongRect, private val ball: Ball, var highScore: Int) {
    var leftPoints = 0
    var rightPoints = 0

    fun lookForPoints() {
        val left = leftRect.x + leftRect.rectWidth
        val right = rightRect.x - rightRect.rectWidth

        if (((ball.x in (left .. (left - ball.dx))) && (ball.y + ball.size/2) in (leftRect.y .. leftRect.y + leftRect.rectHeight)) ||
            (ball.x + ball.size) in (right - ball.dx .. right) && (ball.y + ball.size/2) in (rightRect.y .. rightRect.y + rightRect.rectHeight)) {
            ball.dx = -ball.dx
            ball.move()
        }
    }

    fun score() : Boolean {
        return when {
            ball.x < leftRect.x + leftRect.rectWidth -> {
                rightPoints++
                if (rightPoints > highScore) {
                    highScore = rightPoints
                }
                true
            }
            ball.x + ball.size > rightRect.x - rightRect.rectWidth -> {
                leftPoints++
                if (leftPoints > highScore) {
                    highScore = leftPoints
                }
                true
            }
            else -> false
        }
    }
}