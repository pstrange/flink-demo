package com.flink.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flink.demo.model.data.response.Error
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.Response

open class BaseViewModel : ViewModel() {

    var job: Job? = null
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Error>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        error.postValue(Error(status_message = "Exception handled: ${throwable.localizedMessage}"))
        isLoading.postValue(false)
    }

    fun <T> dispatchWeb(dispatcher: WebDispatcher<T>, callback: (response: Response<T>) -> Unit?){
        isLoading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = dispatcher.execute()
            withContext(Dispatchers.Main) {
                if(response.isSuccessful)
                    callback(response)
                else {
                    val body = response.errorBody()
                    val errorBody = Gson().fromJson(body?.string(), Error::class.java)
                    error.postValue(errorBody)
                }
                isLoading.postValue(false)
            }
        }
    }

    fun dispatchDb(dispatcher: Dispatcher){
        isLoading.postValue(true)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            dispatcher.execute()
            withContext(Dispatchers.Main) {
                isLoading.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    abstract class WebDispatcher<T>{
        abstract suspend fun execute() : Response<T>
    }

    abstract class Dispatcher{
        abstract suspend fun execute()
    }
}