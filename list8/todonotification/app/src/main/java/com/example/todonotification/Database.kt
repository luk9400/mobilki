package com.example.todonotification

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [(Task::class)], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun taskDao(): TaskDAO
}