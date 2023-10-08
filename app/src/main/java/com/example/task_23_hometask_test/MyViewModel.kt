package com.example.task_23_hometask_test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(private val repo: Repository):ViewModel() {
    // private тому, що MutableLiveData можна змінити (postValue, setValue)
    // закриваєм від актівіті - щоб актівіті не посилала дані які потім сама отримувала
    private val _uiState = MutableLiveData<UiState>(UiState.Empty)
    // в LiveData немає (postValue, setValue)
    // так робимо щоб MutableLiveData можна було змінювати з середини ViewModel
    val uiState:LiveData<UiState> = _uiState
    // field injection
    //val repo = MyApplication.getApp().repo
    //для koin рефлексія
    //private val repo : Repository by inject(clazz = Repository::class.java)

    fun getData() {
        _uiState.value = UiState.Processing
        //viewModelScope - корутина знищиться з моделькою
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bitcoinResponse = repo.getCurrencyByName("bitcoin")
                if (bitcoinResponse.isSuccessful) {
                   val data = bitcoinResponse.body()?.data
                   _uiState.postValue(UiState.Result("${data?.id} ${data?.rateUsd}"))

                } else {
                    _uiState.postValue(UiState.Error("Error code ${bitcoinResponse.code()}"))
                }
            } catch (e:Exception) {
                _uiState.postValue(UiState.Error(e.localizedMessage))
            }
        }
    }

     // клас який буде тримати стани UI
    sealed class UiState {
        // object тому, що клас не передає даних
        // будуть перевикористовуватись
        object Empty:UiState()
        object Processing:UiState()
        // при зміні даних Result буде створюватись
        class Result(val title:String):UiState()
        // для роботи з веб
        class Error (val descr:String):UiState()
    }
}
