package com.example.todo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_add_list_item.*

class addListItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list_item)
    }

    fun click(view: View) {
        val myIntent = Intent()
        Log.d("test", "Tu powin:     " + text.text)
        myIntent.putExtra("text", text.text.toString())
        myIntent.putExtra("info", info.text.toString())
        myIntent.putExtra("priority", priority.text.toString())
        myIntent.putExtra("date", date.text.toString())
        setResult(Activity.RESULT_OK, myIntent)
        finish()
    }
}
