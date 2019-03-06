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

    fun search() {
        var hour = findViewById<TimePicker>(R.id.input).hour
        var minute = findViewById<TimePicker>(R.id.input).minute

        var time = hour + minute / 100.0
        println(time)
        var timetables = Timetables()

        for (i in timetables.buses) {
            print(i.time.toString() + "; ")
        }

        var bestBus = timetables.buses.get(0)
        for (i in timetables.buses) {
            if (i.time > time) {
                bestBus = i
                break
            }
        }

        findViewById<TextView>(R.id.busOutput1).text = bestBus.line
        findViewById<TextView>(R.id.timeOutput1).text = bestBus.time.toString()
        findViewById<TextView>(R.id.stopOutput1).text = bestBus.busStop

        var bestBus2 = bestBus
        for (i in timetables.buses) {
            if (i.time > bestBus.time) {
                bestBus2 = i
                break
            }
        }

        findViewById<TextView>(R.id.busOutput2).text = bestBus2.line
        findViewById<TextView>(R.id.timeOutput2).text = bestBus2.time.toString()
        findViewById<TextView>(R.id.stopOutput2).text = bestBus2.busStop
    }

    fun searchButton(view: View) {
        search()
    }
}
