package com.example.newton

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerInit()
        createAPI()
        val newton = retrofit.create(NewtonAPI::class.java)
        val call = newton.getResponse("sin", "1.0")

        call.enqueue( object : Callback<NewtonResponse> {
            override fun onFailure(call: Call<NewtonResponse>, t: Throwable) {
                Log.d("dupa", "fail")
            }

            override fun onResponse(call: Call<NewtonResponse>, response: Response<NewtonResponse>) {
                val body = response.body()
                textView.text = body!!.result
                Log.d("dupa", body.toString())
            }
        })
        val str = spinner.selectedItem
        Log.d("dupa", str.toString())
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
            .baseUrl("https://newton.now.sh")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
