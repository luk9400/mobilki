package com.example.wsiobus

import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TimePicker>(R.id.input).setIs24HourView(true)
    }

    private fun setOutput(bestBus: Bus, busOutput: TextView, timeOutput: TextView, stopOutput: TextView) {
        busOutput.text = bestBus.line
        timeOutput.text = String.format(Locale.US, "%.2f", bestBus.time)
        stopOutput.text = bestBus.busStop
        if (bestBus.busStop == ("D. cmentarz")) {
            stopOutput.setTypeface(null, Typeface.BOLD)
        } else {
            stopOutput.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun search() {
        val hour = input.hour
        val minute = input.minute
        val time = hour + minute / 100.0
        val timetables = Timetables()

        var bestBus = timetables.buses[0]
        var bestBus2 = timetables.buses[1]
        for (i in 0..timetables.buses.size - 1) {
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

        setOutput(bestBus, busOutput1, timeOutput1, stopOutput1)
        setOutput(bestBus2, busOutput2, timeOutput2, stopOutput2)
    }

    fun searchButton(view: View) {
        search()
    }

    fun searchNow(view: View) {
        println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
        println(Calendar.getInstance().get(Calendar.MINUTE))

        input.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        input.minute = Calendar.getInstance().get(Calendar.MINUTE)
        search()
    }
}
