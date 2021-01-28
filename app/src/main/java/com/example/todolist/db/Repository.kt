package com.example.todolist.db

import com.example.todolist.db.dao.ToDoDao
import com.example.todolist.db.model.ToDo
import kotlinx.coroutines.*

open class Repository(private val toDo:ToDoDao) {
    private val repositoryJob = Job()
    private val coroutineScope = CoroutineScope(repositoryJob + Dispatchers.Main)

    fun getAll() = toDo.getAll()

    fun addItem(row: ToDo){
        coroutineScope.launch {
            insert(row)
        }
    }

    fun updateItem(row: ToDo){
        coroutineScope.launch {
            update(row)
        }
    }

    fun deleteItem(row: ToDo){
        coroutineScope.launch {
            delete(row)
        }
    }

    private suspend fun insert(row: ToDo){
        withContext(Dispatchers.IO) {
            toDo.insert(row)
        }
    }

    private suspend fun delete(row: ToDo){
        withContext(Dispatchers.IO) {
            toDo.delete(row)
        }
    }
    private suspend fun update(row: ToDo){
        withContext(Dispatchers.IO) {
            toDo.update(row)
        }
    }
}