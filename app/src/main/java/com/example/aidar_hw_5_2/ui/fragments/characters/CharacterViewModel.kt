package com.example.aidar_hw_5_2.ui.fragments.characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aidar_hw_5_2.data.api.ApiService
import com.example.aidar_hw_5_2.data.model.characters.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    private val _characters = MutableLiveData<BaseResponse>()
    val characters: LiveData<BaseResponse> get() = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getAllCharacters() {
        api.getAllCharacters().enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _characters.postValue(it)
                    }
                } else {
                    _error.postValue("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, thr: Throwable) {
                _error.postValue(thr.localizedMessage ?: "Unknown error")
            }
        })
    }
}