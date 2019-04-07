package com.example.todo

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class MyArrayAdapter(context: Context, var data: ArrayList<ListItem>) :
    ArrayAdapter<ListItem>(context, R.layout.list_item, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item, parent, false)
        }

        view!!.findViewById<TextView>(R.id.text).text = data[position].text
        view.findViewById<TextView>(R.id.date).text = data[position].date
        val id = context.resources.getIdentifier("icon_" + data[position].type + "30dp", "drawable", context.applicationContext.packageName)
        view.findViewById<ImageView>(R.id.imgView).setImageResource(id)
        var color: Int
        when (data[position].priority) {
            1 -> color = Color.parseColor("#d32f2f")
            2 -> color = Color.parseColor("#ffc107")
            else -> color = Color.parseColor("#cddc39")
        }
        view.findViewById<ConstraintLayout>(R.id.constraint).setBackgroundColor(color)

        return view
    }
}