package com.example.todonotification

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @ColumnInfo(name = "text") var text: String?,
    @ColumnInfo(name = "date") var date: String?,
    @ColumnInfo(name = "type") var type: String?,
    @ColumnInfo(name = "priority") var priority: Int,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0
)