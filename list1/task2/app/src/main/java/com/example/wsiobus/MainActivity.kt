package com.example.wsiobus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.TimePicker

class MainActivity : AppCompatActivity() {

    val c845: List<Double> = listOf(4.43, 5.29, 8.15, 12.45, 14.45, 15.45, 19.05)
    val k845: List<Double> = listOf(6.10, 7.15, 10.14, 16.34, 17.54, 18.24, 20.37, 22.32)
    val c865: List<Double> = listOf(5.15, 13.14, 17.17, 21.17)
    val k865: List<Double> = listOf(7.24, 9.13, 11.13, 15.22, 19.21, 23.14)
    val g2: List<Double> = listOf(5.15, 6.02, 6.49, 8.11, 16.08, 17.06, 17.40, 18.44)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun search() {
        var hour = findViewById<TimePicker>(R.id.input).hour
        var minute = findViewById<TimePicker>(R.id.input).minute
        println(hour)
        println(minute)
        var time = hour + minute / 100.0
        println(time)
        var bestBus = c845.get(0)
        for (i in c845) {
            if (i > time) {
                bestBus = i
                break
            }
        }
        findViewById<TextView>(R.id.busOutput1).text = "845"
        findViewById<TextView>(R.id.timeOutput1).text = bestBus.toString()
        findViewById<TextView>(R.id.stopOutput1).text = "Cmentarz"
    }

    fun searchButton(view: View) {
        search()
    }
}
