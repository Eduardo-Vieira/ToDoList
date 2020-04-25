package com.example.todolist.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import kotlinx.android.synthetic.main.activity_form.*

class NewFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

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
