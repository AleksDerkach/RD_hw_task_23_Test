package com.example.task_23_hometask_test

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication:Application() {
    //lateinit var repo: Repository

    override fun onCreate() {
        super.onCreate()
//        startKoin{
//            androidContext(this@MyApplication)
//            modules(myModule)
//        }
        instance = this
        //repo = Repository(getApiClient())
    }

    // забрлаи в модуль
    private fun getApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https:/api.coincap.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object{
        // lateinit для дагер
        private lateinit var instance: MyApplication
        fun getApp() = instance
    }
}