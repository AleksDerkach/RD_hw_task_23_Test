package com.example.task_23_hometask_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.concurrent.Executors


class MyViewModelTest : ViewModel() {

    private val dispatcher = Executors
        .newSingleThreadExecutor()
        .asCoroutineDispatcher()

    @Rule
    fun getRule() = InstantTaskExecutorRule()

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun when_response_is_success_return_update_data() {
        val repo = Mockito.mock(Repository::class.java)
        val successfull_response = BitcoinResponce(Data(id="bitcoin",
            symbol = "BTC",
            currencySymbol="₿",
            rateUsd="27972.7670839058716052"))

        val viewModel = MyViewModel(repo)
        val eventList = mutableListOf<MyViewModel.UiState>()
        viewModel.uiState.observeForever {
            eventList.add(it)
        }
        runBlocking {
            viewModelScope.launch {
            Mockito.`when`(repo.getCurrencyByName("bitcoin").body()?.data?.rateUsd)
                .thenReturn(successfull_response.data?.rateUsd)
        }
        }
        viewModel.getData()
        viewModelScope.launch {
            Assert.assertEquals("27972.7670839058716052", viewModel.uiState)
        }
    }
}