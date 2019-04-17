package com.example.gallery

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity

class SecondActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.second_activity)

//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        val galleryFragment = ImageFullscreenFragment()
//        galleryFragment.show(fragmentTransaction, "gallery")
    }
}