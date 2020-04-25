package com.example.todolist.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.db.model.ToDo

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): LiveData<List<ToDo>>

    @Insert
    suspend fun insert(toDo: ToDo)

    @Delete
    suspend fun delete(toDo: ToDo)

    @Update
    suspend fun update(toDo: ToDo)
}