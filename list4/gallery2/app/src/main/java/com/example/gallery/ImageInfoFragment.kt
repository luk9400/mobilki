package com.example.gallery

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView

class ImageInfoFragment : Fragment() {
    lateinit var titleView: TextView
    lateinit var ratingBar: RatingBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_info, container, false)
        titleView = view.findViewById(R.id.titleView)
        ratingBar = view.findViewById(R.id.ratingBar)

        val position = activity!!.intent.getIntExtra("position", 0)
        ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                val myIntent = Intent(activity, MainActivity::class.java)
                myIntent.putExtra("rating", rating)
                myIntent.putExtra("position", position)
                startActivity(myIntent)
            }
        }
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity?.intent != null) {
            val title = activity!!.intent.getStringExtra("title")
            val rating = activity!!.intent.getFloatExtra("rating", 0f)

            titleView.text = title
            ratingBar.rating = rating
        }
    }
}