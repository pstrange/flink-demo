package com.flink.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.flink.demo.BuildConfig
import com.flink.demo.model.data.response.Authentication
import com.flink.demo.model.data.response.GuestSession
import com.flink.demo.model.repository.Repository
import com.flink.demo.viewmodel.preferences.AppPreferences
import retrofit2.Response

class SplashViewModel(private val repository: Repository) : BaseViewModel() {

    val session = MutableLiveData<GuestSession?>()

    fun authenticate(){
        dispatchWeb(object: WebDispatcher<Authentication>(){
            override suspend fun execute(): Response<Authentication> {
                return repository.authenticate(BuildConfig.API_KEY)
            }
        })
        { response ->
            val body = response.body()
            AppPreferences.AUTH_TOKEN = body?.request_token.toString()
            createSession()
        }
    }

    private fun createSession(){
        dispatchWeb(object: WebDispatcher<GuestSession>(){
            override suspend fun execute(): Response<GuestSession> {
                return repository.createSession(BuildConfig.API_KEY)
            }
        })
        { response ->
            val body = response.body()
            AppPreferences.SESSION_ID = body?.guest_session_id.toString()
            session.postValue(body)
        }
    }

}