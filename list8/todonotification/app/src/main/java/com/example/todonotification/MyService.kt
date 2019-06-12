package com.example.todonotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat

class MyService : Service() {
    private val CHANNEL_ID = "channel"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread {
            if (intent != null) {
                val text = intent.getStringExtra("text")
                val date = intent.getStringExtra("date")
                makeNotification(text, date)
            }

//            val doneIntent = Intent()
//            doneIntent.action = "done"
//            sendBroadcast(doneIntent)
        }.start()

        return START_STICKY
    }

    private fun makeNotification(text: String, date: String) {

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