package com.example.todo

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_add_list_item.*

class addListItem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list_item)
    }

    fun click(view: View) {
        val myIntent = Intent()

        myIntent.putExtra("text", text.text.toString())
        myIntent.putExtra("date", date.text.toString())
        val type = findViewById<RadioButton>(imgView.checkedRadioButtonId).text.toString().toLowerCase()
        myIntent.putExtra("type", type)
        val priority = findViewById<RadioButton>(priority.checkedRadioButtonId).text.toString()
        myIntent.putExtra("priority", priority)

        setResult(Activity.RESULT_OK, myIntent)
        finish()
    }
}
