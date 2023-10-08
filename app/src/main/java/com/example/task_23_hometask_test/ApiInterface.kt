package com.example.task_23_hometask_test

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/v2/rates/{cryptoName}")
    // в такому ми не розуміємо помилки інтернет взаємодії
    //suspend fun getCryptoByName(@Path("cryptoName") name : String):BitcoinResponce

    // Response це клас Retrofit де є подробиці
    suspend fun getCryptoByName(@Path("cryptoName") name : String
    ): Response<BitcoinResponce>
}