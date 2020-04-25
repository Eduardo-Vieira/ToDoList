package com.example.todolist.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.todolist.R
import com.example.todolist.db.Repository
import com.example.todolist.db.model.ToDo
import kotlinx.android.synthetic.main.activity_form.*
import org.koin.android.ext.android.inject

class EditFormActivity : AppCompatActivity() {

    private val db: Repository by inject()

    private lateinit var data:ToDo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_form)

        val dataExtra = intent
        if(dataExtra.hasExtra("ToDo")){
            val data = dataExtra.getSerializableExtra("ToDo") as ToDo
            edit_Task.setText(data.task)
        }

        button_save.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(edit_Task.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val task = edit_Task.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, task)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.todolist.REPLY"
    }
}
