package com.example.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class MyArrayAdapter(context: Context, var data: ArrayList<String>) :
    ArrayAdapter<String>(context, R.layout.my_layout_item, data) {

    // ViewHolder!!

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.my_layout_item, parent, false)
        }
        view!!.findViewById<TextView>(R.id.textView).text = data[position]
        return view
    }
}