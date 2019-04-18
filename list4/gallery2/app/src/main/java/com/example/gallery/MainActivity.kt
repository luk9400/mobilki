package com.example.gallery

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

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

        val file = File(this.filesDir, "items.json")
        if (file.exists()) {
            val itemsJson = JSONArray(file.readText())
            for (i in 0 until itemsJson.length()) {
                imageList.add(Image(itemsJson[i] as JSONObject))
            }
        } else {
            val string = application.assets.open("items.json").bufferedReader().use{
                it.readText()
            }
            val itemsJson = JSONArray(string)
            for (i in 0 until itemsJson.length()) {
                imageList.add(Image(itemsJson[i] as JSONObject))
            }
        }

        if (intent != null) {
            val position = intent.getIntExtra("position", 0)
            val rating = intent.getFloatExtra("rating", 0f)
            imageList[position].rating = rating
            imageList.sortBy {
                it.rating
            }
            galleryAdapter.notifyDataSetChanged()
        }
    }

    private fun loadImages() {
        imageList.add(Image("https://i.ibb.co/wBYDxLq/beach.jpg", "Beach Houses", 0f))
        imageList.add(Image("https://i.ibb.co/gM5NNJX/butterfly.jpg", "Butterfly", 0f))
        imageList.add(Image("https://i.ibb.co/10fFGkZ/car-race.jpg", "Car Racing", 0f))
        imageList.add(Image("https://i.ibb.co/ygqHsHV/coffee-milk.jpg", "Coffee with Milk", 0f))
        imageList.add(Image("https://i.ibb.co/7XqwsLw/fox.jpg", "Fox", 1f))
        imageList.add(Image("https://i.ibb.co/L1m1NxP/girl.jpg", "Mountain Girl", 0f))
        imageList.add(Image("https://i.ibb.co/wc9rSgw/desserts.jpg", "Desserts Table", 0f))
        imageList.add(Image("https://i.ibb.co/wdrdpKC/kitten.jpg", "Kitten", 0f))
        imageList.add(Image("https://i.ibb.co/dBCHzXQ/paris.jpg", "Paris Eiffel", 0f))
        imageList.add(Image("https://i.ibb.co/JKB0KPk/pizza.jpg", "Pizza Time", 0f))
        imageList.add(Image("https://i.ibb.co/VYYPZGk/salmon.jpg", "Salmon ", 0f))
        imageList.add(Image("https://i.ibb.co/JvWpzYC/sunset.jpg", "Sunset in Beach", 0f))
        imageList.sortBy {
            it.rating
        }
        galleryAdapter.notifyDataSetChanged()
    }

    override fun click(position: Int) {
        val myIntent = Intent(this, SecondActivity::class.java)
        myIntent.putExtra("url", imageList[position].imageUrl)
        myIntent.putExtra("title", imageList[position].title)
        myIntent.putExtra("rating", imageList[position].rating)
        myIntent.putExtra("position", position)

        startActivity(myIntent)
    }

    override fun onStop() {
        super.onStop()
        val jsonArray = JSONArray()
        imageList.forEach { jsonArray.put(it.toJson()) }
        File(this.filesDir, "items.json").printWriter().use {
            it.println(jsonArray.toString())
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
