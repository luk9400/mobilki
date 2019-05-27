package com.example.todosql

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

        val requestCode = intent.getStringExtra("requestCode")

        if (requestCode != null && requestCode.toInt() == 1337) {
            text.setText(intent.getStringExtra("text"))
            when (intent.getStringExtra("priority").toInt()) {
                1 -> prior1.isChecked = true
                2 -> prior2.isChecked = true
                else -> prior3.isChecked = true
            }
            when (intent.getStringExtra("type")) {
                "work" -> work.isChecked = true
                "home" -> home.isChecked = true
                else -> school.isChecked = true
            }
            val dateString = intent.getStringExtra("date")
            itemYear = dateString.substring(0, 4).toInt()
            itemMonth = dateString.substring(5, 7).toInt()
            itemDay = dateString.substring(8, 10).toInt()
        } else {
            val c = Calendar.getInstance()
            itemYear = c.get(Calendar.YEAR)
            itemMonth = c.get(Calendar.MONTH) + 1
            itemDay = c.get(Calendar.DAY_OF_MONTH)
        }
    }

    fun click(view: View) {
        val myIntent = Intent()

        var dateString = itemYear.toString() + "-"
        if ((itemMonth!!.plus(1)) > 9) {
            dateString += itemMonth.toString() + "-"
        } else {
            dateString += "0" + itemMonth + "-"
        }
        if (itemDay!! > 9) {
            dateString += itemDay
        } else {
            dateString += "0" + itemDay
        }

        myIntent.putExtra("text", text.text.toString())
        myIntent.putExtra("date", dateString)
        val type = findViewById<RadioButton>(imgView.checkedRadioButtonId).text.toString().toLowerCase()
        myIntent.putExtra("type", type)
        val priority = findViewById<RadioButton>(priority.checkedRadioButtonId).text.toString()
        myIntent.putExtra("priority", priority)
        myIntent.putExtra("position", intent.getStringExtra("position")?.toString())

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
