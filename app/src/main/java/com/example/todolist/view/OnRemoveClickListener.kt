package com.example.todolist.view

import com.example.todolist.db.model.ToDo

interface OnRemoveClickListener {
    fun onItemClick( item: ToDo?, posicao:Int)
}