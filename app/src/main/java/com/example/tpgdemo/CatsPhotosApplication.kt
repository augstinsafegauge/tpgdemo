package com.example.tpgdemo
import android.app.Application
import com.example.tpgdemo.data.AppContainer
import com.example.tpgdemo.data.DefaultAppContainer

class CatsPhotosApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}