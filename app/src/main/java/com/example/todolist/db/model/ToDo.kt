package com.example.todolist.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "task") val task: String?
)