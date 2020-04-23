package com.example.todolist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.todolist.R
import com.example.todolist.db.Repository
import com.example.todolist.db.model.ToDo
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val db: Repository by inject()
    private val adapter = AdapterListToDo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        listToDoRecyclerView.addItemDecoration(divider)

        listToDoRecyclerView.adapter = adapter

        adapter.setOnRemoveClickListener(object :OnRemoveClickListener {
            override fun onItemClick(item: ToDo?, posicao: Int) {
                item?.let { db.deleteItem(it) }
            }
        })

        db.getAll().observe(this, Observer {
            adapter.update(it)
        })

        buttonIserir.setOnClickListener {
           db.addItem(ToDo(null, editTask.text.toString()))
        }

    }

}
