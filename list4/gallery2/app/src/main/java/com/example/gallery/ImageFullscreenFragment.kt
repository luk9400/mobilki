package com.example.gallery

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image_fullscreen.view.*

class ImageFullscreenFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_fullscreen, container, false)
        Picasso.get().load("https://i.ibb.co/7XqwsLw/fox.jpg").into(view.fullscreenImage)
        container?.addView(view)
        return view
    }
}