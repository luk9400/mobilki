package com.example.gallery

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GalleryImageClickListener {

    private val imageList = ArrayList<Image>()
    lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        galleryAdapter = GalleryAdapter(imageList)
        galleryAdapter.listener = this

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = galleryAdapter

        loadImages()
    }

    private fun loadImages() {
        imageList.add(Image("https://i.ibb.co/wBYDxLq/beach.jpg", "Beach Houses",0f))
        imageList.add(Image("https://i.ibb.co/gM5NNJX/butterfly.jpg", "Butterfly",0f))
        imageList.add(Image("https://i.ibb.co/10fFGkZ/car-race.jpg", "Car Racing",0f))
        imageList.add(Image("https://i.ibb.co/ygqHsHV/coffee-milk.jpg", "Coffee with Milk",0f))
        imageList.add(Image("https://i.ibb.co/7XqwsLw/fox.jpg", "Fox",1f))
        imageList.add(Image("https://i.ibb.co/L1m1NxP/girl.jpg", "Mountain Girl",0f))
        imageList.add(Image("https://i.ibb.co/wc9rSgw/desserts.jpg", "Desserts Table",0f))
        imageList.add(Image("https://i.ibb.co/wdrdpKC/kitten.jpg", "Kitten",0f))
        imageList.add(Image("https://i.ibb.co/dBCHzXQ/paris.jpg", "Paris Eiffel",0f))
        imageList.add(Image("https://i.ibb.co/JKB0KPk/pizza.jpg", "Pizza Time",0f))
        imageList.add(Image("https://i.ibb.co/VYYPZGk/salmon.jpg", "Salmon ",0f))
        imageList.add(Image("https://i.ibb.co/JvWpzYC/sunset.jpg", "Sunset in Beach",0f))
        imageList.sortBy {
            it.rating
        }
        galleryAdapter.notifyDataSetChanged()
    }

    override fun click(position: Int) {
        val myIntent = Intent(this, SecondActivity::class.java)
        startActivity(myIntent)
    }
}
