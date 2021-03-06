package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import java.io.Serializable
import java.util.prefs.NodeChangeListener

class ImageInfoFragment : Fragment() {
    lateinit var titleView: TextView
    lateinit var ratingBar: RatingBar
    lateinit var callback: OnRatingBarClickListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_info, container, false)
        titleView = view.findViewById(R.id.titleView)
        ratingBar = view.findViewById(R.id.ratingBar)

        val position = activity!!.intent.getIntExtra("position", 0)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
                if (fromUser) {
                    callback.onRatingBarClick(rating, position)
                }
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

        if (arguments != null) {
            val title = (arguments!!.get("image") as Image).title
            val rating = (arguments!!.get("image") as Image).rating
            val position = arguments!!.getInt("position")

            titleView.text = title
            ratingBar.rating = rating

            ratingBar.setOnRatingBarChangeListener { _, ratingB, fromUser ->
                if (fromUser) {
                    if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        (activity as MainActivity).setRating(position, ratingB)
                    }
                }
            }
        }
    }

    fun setListener(listener: OnRatingBarClickListener) {
        callback = listener
    }

    companion object {
        fun newInstance(position: Int, image: Serializable): ImageInfoFragment {
            val frag = ImageInfoFragment()
            val bundle = Bundle()
            bundle.putSerializable("image", image)
            bundle.putInt("position", position)
            frag.arguments = bundle

            return frag
        }
    }

    interface OnRatingBarClickListener {
        fun onRatingBarClick(rating: Float, position: Int)
    }
}