package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

class SecondActivity : AppCompatActivity(), ImageInfoFragment.OnRatingBarClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.second_activity)
            for (frag in supportFragmentManager.fragments) {
                if (frag is ImageInfoFragment) {
                    frag.setListener(this)
                }
            }

        } else {
            val myIntent = Intent(this, MainActivity::class.java)
            myIntent.putExtra("position", intent.getIntExtra("position", 0))
            myIntent.putExtra("requestCode", 1337)
            startActivity(myIntent)
        }

    }

    override fun onRatingBarClick(rating: Float, position: Int) {
        val myIntent = Intent()
        myIntent.putExtra("rating", rating)
        myIntent.putExtra("position", position)
        setResult(Activity.RESULT_OK, myIntent)
    }
}