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

    private val newFormActivityRequestCode = 1
    private val editFormActivityRequestCode = 2

    private val db: Repository by inject()
    private val adapter = AdapterListToDo()
    private var uidEdit = 0

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mnu_mainactivity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nmu_new_item -> showNewFormActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showNewFormActivity(){
        val intent = Intent(this@MainActivity, NewFormActivity::class.java)
        startActivityForResult(intent, newFormActivityRequestCode)
    }

    fun showEditFormActivity(item:ToDo){
        val intent = Intent(this@MainActivity, EditFormActivity::class.java)
        intent.putExtra("ToDo", item)
        startActivityForResult(intent, editFormActivityRequestCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        listToDoRecyclerView.addItemDecoration(divider)

        listToDoRecyclerView.adapter = adapter

        adapter.setOnRemoveClickListener(object :OnClickListener {
            override fun onItemClick(item: ToDo?, posicao: Int) {
                item?.let { removeItem(it) }
            }
        })

        adapter.setOnEditClickListener(object :OnClickListener{
            override fun onItemClick(item: ToDo?, posicao: Int) {
                item?.let {
                    uidEdit = it.uid!!
                    showEditFormActivity(it)
                }
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

        if (requestCode == newFormActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewFormActivity.EXTRA_REPLY)?.let {
                db.addItem(ToDo(null, it))
            }
        }
        else if (requestCode == editFormActivityRequestCode  && resultCode == Activity.RESULT_OK){
            data?.getStringExtra(EditFormActivity.EXTRA_REPLY)?.let {
                db.updateItem(ToDo(uidEdit, it))
            }
        }
        else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}
