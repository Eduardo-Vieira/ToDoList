package com.example.todolist.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.io.Serializable

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true) val uid: Int?,
    @ColumnInfo(name = "task") var task: String?,
    @ColumnInfo(name = "checked") var checked: Boolean? = null
):Serializable