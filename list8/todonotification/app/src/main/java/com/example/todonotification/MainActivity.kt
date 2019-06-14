package com.example.todonotification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.arch.persistence.room.Room
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private val listViewItems = ArrayList<Task>()
    private var myAdapter: MyArrayAdapter? = null
    private lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        myAdapter = MyArrayAdapter(this, listViewItems)

        listView.adapter = myAdapter

        AsyncTask.execute {
            try {
                db = Room.databaseBuilder(this, Database::class.java, "todo.db").build()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            listViewItems.addAll(db.taskDao().getAll())
            //Log.d("dupa", listViewItems.toString())
        }

        fab.setOnClickListener {
            val myIntent = Intent(this, AddListItemActivity::class.java)
            startActivityForResult(myIntent, 2137)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val task = listViewItems[position]
            AsyncTask.execute {
                db.taskDao().deleteTask(task)
            }
            listViewItems.removeAt(position)
            myAdapter!!.notifyDataSetChanged()
            false
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val myIntent = Intent(this, AddListItemActivity::class.java)
            val requestCode = 1337
            myIntent.putExtra("id", listViewItems[position].id.toString())
            myIntent.putExtra("text", listViewItems[position].text.toString())
            myIntent.putExtra("date", listViewItems[position].date.toString())
            myIntent.putExtra("type", listViewItems[position].type.toString())
            myIntent.putExtra("priority", listViewItems[position].priority.toString())
            myIntent.putExtra("requestCode", requestCode.toString())
            myIntent.putExtra("position", position.toString())

            startActivityForResult(myIntent, requestCode)
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
                listViewItems.sortBy { it.priority }
                myAdapter!!.notifyDataSetChanged()
                true
            }
            R.id.sort_type -> {
                listViewItems.sortBy { e ->
                    e.type
                }
                myAdapter!!.notifyDataSetChanged()
                true
            }
            R.id.sort_date -> {
                listViewItems.sortBy { it.date }
                myAdapter!!.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2137) {
            try {
                val text = data?.getStringExtra("text")
                val priority = data?.getStringExtra("priority")
                val date = data?.getStringExtra("date")
                val type = data?.getStringExtra("type")

                val task = Task(text, date, type, priority!!.toInt())
                listViewItems.add(task)
                AsyncTask.execute {
                    db.taskDao().insertAll(task)
                }
                myAdapter!!.notifyDataSetChanged()

                setNotification(text, date)
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }

        if (requestCode == 1337) {
            try {
                val position = data?.getStringExtra("position")?.toInt()
                val id = data?.getStringExtra("id")?.toLong()
                if (position != null && id != null) {
                    val text = data.getStringExtra("text")
                    val date = data.getStringExtra("date")
                    val type = data.getStringExtra("type")
                    val priority = data.getStringExtra("priority").toInt()
                    listViewItems[position].text = text
                    listViewItems[position].date = date
                    listViewItems[position].type = type
                    listViewItems[position].priority = priority
                    AsyncTask.execute {
                        db.taskDao().update(id, text, date, type, priority)
                    }
                    myAdapter!!.notifyDataSetChanged()
                }
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
        }
    }

    private fun setNotification(text: String?, date: String?) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                makeNotification(text, date)
                Log.d("dupa", "powiadomionko")
            }
        }
        registerReceiver(receiver, IntentFilter("notification"))

        val intent = Intent()
        intent.action = "notification"

        val alarmManager = getSystemService(ALARM_SERVICE) as? AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(
                date!!.substring(0, 4).toInt(),
                date!!.substring(5, 7).toInt() - 1,
                date!!.substring(8, 10).toInt(),
                15,
                0
            )
        }

        alarmManager?.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun makeNotification(text: String?, date: String?) {
        val CHANNEL_ID = "channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT)
            val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(text)
                .setContentText(date)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.notification_icon_background)

            val myIntent = Intent(this, MainActivity::class.java)
            val pending = PendingIntent.getActivity(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setContentIntent(pending)

            val notification = builder.build()
            manager.notify(1234, notification)
        }
    }
}
