package com.example.todolist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.db.dao.ToDoDao
import com.example.todolist.db.model.ToDo

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}