package com.example.gallery

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.second_activity)
        } else {
            val myIntent = Intent(this, MainActivity::class.java)
            myIntent.putExtra("position", intent.getIntExtra("position", 0))
            myIntent.putExtra("requestCode", 1337)
            startActivity(myIntent)
        }

    }
}