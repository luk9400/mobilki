package com.example.gallery

import android.content.Intent
import android.content.res.Configuration
import android.graphics.ImageDecoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.android.synthetic.main.activity_main_land.*
import kotlinx.android.synthetic.main.fragment_image_fullscreen.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.lang.NullPointerException

class MainActivity : AppCompatActivity(), GalleryImageClickListener {

    private val imageList = ArrayList<Image>()
    lateinit var galleryAdapter: GalleryAdapter
    private var imageFullscreenFragment = ImageFullscreenFragment()
    private var imageInfoFragment = ImageInfoFragment()
    private var currentPhoto = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main)
        } else {
            setContentView(R.layout.activity_main_land)
        }

        galleryAdapter = GalleryAdapter(imageList)
        galleryAdapter.listener = this

        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = galleryAdapter

        loadImages()

        if (intent != null && resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            currentPhoto = intent.getIntExtra("position", 0)
            pickImage(currentPhoto)
        }
    }

    fun loadImages() {
        val file = File(this.filesDir, "items.json")
        if (file.exists()) {
            val itemsJson = JSONArray(file.readText())
            for (i in 0 until itemsJson.length()) {
                imageList.add(Image(itemsJson[i] as JSONObject))
            }
        } else {
            val string = application.assets.open("items.json").bufferedReader().use {
                it.readText()
            }
            val itemsJson = JSONArray(string)
            for (i in 0 until itemsJson.length()) {
                imageList.add(Image(itemsJson[i] as JSONObject))
            }
        }
    }

    override fun click(position: Int) {
        currentPhoto = position
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val myIntent = Intent(this, SecondActivity::class.java)
            myIntent.putExtra("url", imageList[position].imageUrl)
            myIntent.putExtra("title", imageList[position].title)
            myIntent.putExtra("rating", imageList[position].rating)
            myIntent.putExtra("position", position)

            startActivityForResult(myIntent, 2137)
        } else {
            pickImage(position)
        }
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

    fun setRating(position: Int, rating: Float) {
        imageList[position].rating = rating

        imageList.sortBy { it.rating }
        galleryAdapter.notifyDataSetChanged()
    }

    private fun pickImage(position: Int) {
        supportFragmentManager.beginTransaction().remove(imageFullscreenFragment).commit()
        imageFullscreenFragment = ImageFullscreenFragment.newInstance(imageList[position])
        supportFragmentManager.beginTransaction().add(R.id.imageFrame, imageFullscreenFragment).commit()

        supportFragmentManager.beginTransaction().remove(imageInfoFragment).commit()
        imageInfoFragment = ImageInfoFragment.newInstance(position, imageList[position])
        supportFragmentManager.beginTransaction().add(R.id.infoFrame, imageInfoFragment).commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2137) {
            try {
                val position = data?.getIntExtra("position", 0)
                val rating = data?.getFloatExtra("rating", 0f)
                imageList[position!!].rating = rating!!
                imageList.sortBy {
                    it.rating
                }
                galleryAdapter.notifyDataSetChanged()
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }
}
