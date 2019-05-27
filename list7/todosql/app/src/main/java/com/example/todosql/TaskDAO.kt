package com.example.todosql

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TaskDAO {
    @Query("select * from tasks")
    fun getAll() : List<Task>

    @Insert
    fun insertAll(vararg task: Task)
}