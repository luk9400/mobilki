package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.list_item.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    private val listViewItems = ArrayList<ListItem>()
    private var myAdapter: MyArrayAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        myAdapter = MyArrayAdapter(this, listViewItems)

        listView.adapter = myAdapter

        try {
            val itemsJson = JSONArray(File(this.filesDir, "items.json").readText())
            for (i in 0 until itemsJson.length()) {
                listViewItems.add(ListItem(itemsJson[i] as JSONObject))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }


        fab.setOnClickListener {
            val myIntent = Intent(this, addListItem::class.java)
            startActivityForResult(myIntent, 2137)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            listViewItems.removeAt(position)
            myAdapter!!.notifyDataSetChanged()
            false
        }

        listView.setOnItemClickListener { _, view, position, _ ->
            val myIntent = Intent(this, addListItem::class.java)
            myIntent.putExtra("text", listViewItems.get(position).text.toString())
            myIntent.putExtra("date", listViewItems.get(position).date.toString())
            myIntent.putExtra("type", listViewItems.get(position).type.toString())
            myIntent.putExtra("priority", listViewItems.get(position).priority.toString())

            startActivityForResult(myIntent, 1337)
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
            R.id.sort_priority -> {
                listViewItems.sortWith(PriorityComparatorListItem)
                myAdapter!!.notifyDataSetChanged()
                true
            }
            R.id.sort_type -> {
                listViewItems.sortBy {e ->
                    e.type
                }
                myAdapter!!.notifyDataSetChanged()
                true
            }
            R.id.sort_date -> {
                listViewItems.sortBy {it.date}
                myAdapter!!.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 2137) {
            val text = data?.getStringExtra("text")
            val priority = data?.getStringExtra("priority")
            val date = data?.getStringExtra("date")
            val type = data?.getStringExtra("type")

            listViewItems.add(ListItem(text, date, type, priority!!.toInt()))
            myAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onStop() {
        super.onStop()
        val jsonArray = JSONArray()
        listViewItems.forEach {e -> jsonArray.put(e.toJson())}
        File(this.filesDir, "items.json").printWriter().use {out ->
            out.println(jsonArray.toString())
        }
    }

//    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
//
//        outState?.putSerializable("items", listViewItems)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
//        super.onRestoreInstanceState(savedInstanceState)
//
//        listViewItems.addAll(savedInstanceState?.getSerializable("items") as ArrayList<ListItem>)
//        myAdapter!!.notifyDataSetChanged()
//    }
}
