package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    var listViewItems: ArrayList<listItem> = arrayListOf()
    var myAdapter: MyArrayAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        myAdapter = MyArrayAdapter(this, listViewItems)

        listView.adapter = myAdapter

        fab.setOnClickListener {
            val myIntent = Intent(this, addListItem::class.java)
            startActivityForResult(myIntent, 2137)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            listViewItems.removeAt(position)
            myAdapter!!.notifyDataSetChanged()
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 2137) {
            val text = data?.getStringExtra("text")
            val info = data?.getStringExtra("info")
            val priority = data?.getStringExtra("priority")
            val date = data?.getStringExtra("date")

            Log.d("test", "test:          " + text)

            listViewItems.add(listItem(text, info, priority, date))
            myAdapter!!.notifyDataSetChanged()
        }
    }
}
