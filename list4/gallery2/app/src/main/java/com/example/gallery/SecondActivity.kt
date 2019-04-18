package com.example.gallery

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.second_activity)
        } else {
            setContentView(R.layout.second_activity_land)
        }

    }
}