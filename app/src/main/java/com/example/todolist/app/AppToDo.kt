package com.example.todolist.app

import android.app.Application
import com.example.todolist.app.daoModule
import com.example.todolist.app.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppToDo:Application() {
    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@AppToDo)
            // declare modules
            modules(listOf(
                databaseModule,
                daoModule
            ))
        }
    }
}