package com.example.task_23_hometask_test

import android.provider.ContactsContract

data class BitcoinResponce(val data: Data?)

data class Data(
    val id:String,
    val symbol:String,
    val currencySymbol:String,
    val rateUsd:String
)
