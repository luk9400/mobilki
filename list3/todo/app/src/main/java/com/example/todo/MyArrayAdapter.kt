package com.example.todo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class MyArrayAdapter(context: Context, var data: ArrayList<listItem>) :
    ArrayAdapter<listItem>(context, R.layout.list_item, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item, parent, false)
        }

        Log.d("test", position.toString())
        Log.d("test", data[position].text)
        view!!.findViewById<TextView>(R.id.text).text = data[position].text

        return view
    }
}