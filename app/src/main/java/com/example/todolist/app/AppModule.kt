package com.example.todolist.app

import androidx.room.Room
import com.example.todolist.db.Repository
import com.example.todolist.db.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "dbToDo"
        ).build()
    }
}
val daoModule = module {
    //DAO
    single { get<AppDatabase>().toDoDao() }
    //Repository
    single { Repository(get()) }
}