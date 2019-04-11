package com.example.todo

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_add_list_item.*
import java.util.*

class addListItem : AppCompatActivity() {
    var itemYear: Int? = null
    var itemMonth: Int? = null
    var itemDay: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_list_item)

        val c = Calendar.getInstance()
        itemYear = c.get(Calendar.YEAR)
        itemMonth = c.get(Calendar.MONTH)
        itemDay = c.get(Calendar.DAY_OF_MONTH)
    }

    fun click(view: View) {
        val myIntent = Intent()

        val dateString = itemYear.toString() + "-" + (itemMonth!!.plus(1)) + "-" + itemDay
        myIntent.putExtra("text", text.text.toString())
        myIntent.putExtra("date", dateString)
        val type = findViewById<RadioButton>(imgView.checkedRadioButtonId).text.toString().toLowerCase()
        myIntent.putExtra("type", type)
        val priority = findViewById<RadioButton>(priority.checkedRadioButtonId).text.toString()
        myIntent.putExtra("priority", priority)

        setResult(Activity.RESULT_OK, myIntent)
        finish()
    }

    fun dateClick(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
            Log.d("test", day.toString() + " " + month + " " + year)
            itemDay = day
            itemMonth = month
            itemYear = year
        }, year, month, day)
        datePickerDialog.show()
    }
}
