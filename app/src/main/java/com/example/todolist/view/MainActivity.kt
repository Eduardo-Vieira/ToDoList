package com.example.todolist.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.db.Repository
import com.example.todolist.db.model.ToDo
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val formActivityRequestCode = 1

    private val db: Repository by inject()
    private val adapter = AdapterListToDo()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mnu_mainactivity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nmu_new_item -> showFormActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showFormActivity(){
        val intent = Intent(this@MainActivity, FormActivity::class.java)
        startActivityForResult(intent, formActivityRequestCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        listToDoRecyclerView.addItemDecoration(divider)

        listToDoRecyclerView.adapter = adapter

        adapter.setOnRemoveClickListener(object :OnRemoveClickListener {
            override fun onItemClick(item: ToDo?, posicao: Int) {
                item?.let { removeItem(it) }
            }
        })

        db.getAll().observe(this, Observer {
            adapter.update(it)
        })

        val recyclerItemTouchHelper = object :RecyclerItemTouchHelper(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position:Int = viewHolder.adapterPosition
                val item = adapter.getItemList(position)
                removeItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(recyclerItemTouchHelper)
        itemTouchHelper.attachToRecyclerView(listToDoRecyclerView)
    }

    private fun removeItem(item:ToDo){
        db.deleteItem(item)
        Toast.makeText(
            applicationContext,
            R.string.delete_task,
            Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == formActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(FormActivity.EXTRA_REPLY)?.let {
                db.addItem(ToDo(null, it))
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}
