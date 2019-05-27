package com.example.newton

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var retrofit: Retrofit
    lateinit var paths: Array<String>
    private val map: MutableMap<String, Int> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val operations = resources.getStringArray(R.array.operations)
        for ((i, op) in operations.withIndex()) {
            map[op] = i
        }
        paths = resources.getStringArray(R.array.paths)

        spinnerInit()
        createAPI()
    }

    private fun spinnerInit() {
        ArrayAdapter.createFromResource(
            this,
            R.array.operations,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun createAPI() {
        retrofit = Retrofit.Builder()
            //.baseUrl("https://newton.now.sh")
            .baseUrl("http://156.17.7.48:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun click(view: View) {
        val newton = retrofit.create(NewtonAPI::class.java)
        val call = newton.getResponse(paths[map[spinner.selectedItem]!!], editText.text.toString())

        call.enqueue( object : Callback<NewtonResponse> {
            override fun onFailure(call: Call<NewtonResponse>, t: Throwable) {
                Log.d("dupa", "fail")
            }

            override fun onResponse(call: Call<NewtonResponse>, response: Response<NewtonResponse>) {
                try {
                    val body = response.body()
                    textView.text = body!!.result.toString()
                    Log.d("dupa", body.toString())
                } catch (e: NullPointerException) {
                    e.printStackTrace()
                }
            }
        })
    }
}
