package com.example.todolist.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.todolist.db.model.ToDo

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): LiveData<List<ToDo>>

    @Insert
    suspend fun insert(toDo: ToDo)

    @Delete
    suspend fun delete(toDo: ToDo)
}