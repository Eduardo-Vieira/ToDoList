package com.example.todolist.view

import com.example.todolist.db.model.ToDo

interface OnClickListener {
    fun onItemClick( item: ToDo?, posicao:Int)
}