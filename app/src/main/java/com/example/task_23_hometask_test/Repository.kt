package com.example.task_23_hometask_test

import retrofit2.Response
import retrofit2.Retrofit

// З репозиторію починається dep. injection. Трохи ручний.
class Repository(private val client: Retrofit) {
    private val apiInterface = client.create(ApiInterface::class.java)
//    suspend fun getCurrencyByName(name:String):BitcoinResponce{
//        return apiInterface.getCryptoByName(name)
//    }
    suspend fun getCurrencyByName(name:String): Response<BitcoinResponce>
    = apiInterface.getCryptoByName(name)
}