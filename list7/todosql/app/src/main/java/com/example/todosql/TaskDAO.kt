package com.example.todosql

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TaskDAO {
    @Query("select * from tasks")
    fun getAll() : List<Task>

    @Query("update tasks set text = :text, date = :date, type = :type, priority = :priority where id = :id")
    fun update(id: Long, text: String?, date: String?, type: String?, priority: Int)

    @Insert
    fun insertAll(vararg task: Task)
}