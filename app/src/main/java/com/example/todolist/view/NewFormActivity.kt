package com.example.todolist.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R

class NewFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val editTask = this.findViewById<TextView>(R.id.edit_Task)
        val buttonSave = this.findViewById<Button>(R.id.button_save)

        buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTask.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val task = editTask.text.toString()
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
