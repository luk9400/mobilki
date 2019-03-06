package com.example.wsiobus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.support.v7.widget.AppCompatEditText
import android.text.format.Time
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val c845: List<Double> = listOf(4.43, 5.29, 8.15, 12.45, 14.45, 15.45, 19.05)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun search() {
        var hour = findViewById<AppCompatEditText>(R.id.input).text
        println(hour)
        findViewById<TextView>(R.id.busOutput1).text = "845"
        findViewById<TextView>(R.id.timeOutput1).text = c845.get(0).toString()
        findViewById<TextView>(R.id.stopOutput1).text = "Cmentarz"
    }

    fun searchButton(view: View) {
        search()
    }
}
