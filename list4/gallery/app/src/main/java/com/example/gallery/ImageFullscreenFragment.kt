package com.example.gallery

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image_fullscreen.view.*
import java.io.Serializable

class ImageFullscreenFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_fullscreen, container, false)

        if (activity?.intent != null) {
            val  imageUrl = activity!!.intent.getStringExtra("url")
            Picasso.get().load(imageUrl).into(view.fullscreenImage)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        if (arguments != null) {
            val image = arguments!!.get("image") as Image
            Picasso.get().load(image.imageUrl).into(view!!.fullscreenImage)
        }
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance(image: Serializable): ImageFullscreenFragment {
            val frag = ImageFullscreenFragment()
            val bundle = Bundle()
            bundle.putSerializable("image", image)
            frag.arguments = bundle

            return frag
        }
    }
}