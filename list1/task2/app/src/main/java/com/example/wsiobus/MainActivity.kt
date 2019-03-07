package com.example.wsiobus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.TimePicker

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TimePicker>(R.id.input).setIs24HourView(true)
    }

    private fun search() {
        val hour = findViewById<TimePicker>(R.id.input).hour
        val minute = findViewById<TimePicker>(R.id.input).minute
        val time = hour + minute / 100.0
        val timetables = Timetables()

        var bestBus = timetables.buses[0]
        var bestBus2 = timetables.buses[1]
        for (i in 0 .. timetables.buses.size - 1) {
            if (timetables.buses[i].time > time) {
                bestBus = timetables.buses[i]
                if (i == timetables.buses.size - 1) {
                    bestBus2 = timetables.buses[0]
                } else {
                    bestBus2 = timetables.buses[i + 1]
                }
                break
            }
        }

        findViewById<TextView>(R.id.busOutput1).text = bestBus.line
        findViewById<TextView>(R.id.timeOutput1).text = bestBus.time.toString()
        findViewById<TextView>(R.id.stopOutput1).text = bestBus.busStop

        findViewById<TextView>(R.id.busOutput2).text = bestBus2.line
        findViewById<TextView>(R.id.timeOutput2).text = bestBus2.time.toString()
        findViewById<TextView>(R.id.stopOutput2).text = bestBus2.busStop
    }

    fun searchButton(view: View) {
        search()
    }
}
