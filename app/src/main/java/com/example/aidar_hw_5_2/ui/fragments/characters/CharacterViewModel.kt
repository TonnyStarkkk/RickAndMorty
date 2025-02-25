package com.example.aidar_hw_5_2.ui.fragments.characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aidar_hw_5_2.data.api.ApiService
import com.example.aidar_hw_5_2.data.local.CharacterEntity
import com.example.aidar_hw_5_2.data.local.CharactersDao
import com.example.aidar_hw_5_2.data.model.characters.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val api: ApiService,
    private val dao: CharactersDao
) : ViewModel() {

    val localCharacters: LiveData<List<CharacterEntity>> = dao.getAllCharacters()

    private val _characters = MutableLiveData<BaseResponse>()
    val characters: LiveData<BaseResponse> get() = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getAllCharacters() {
        viewModelScope.launch {
            try {
                val response = api.getAllCharacters().execute()
                if (response.isSuccessful) {
                    response.body()?.results?.let { characters ->
                        val entities = characters.map {
                            CharacterEntity(
                                id = it.id,
                                name = it.name,
                                gender = it.gender,
                                image = it.image,
                                status = it.status
                            )
                        }

                        dao.clearCharacters()
                        dao.insertCharacters(entities)
                    }
                } else {
                    _error.postValue("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            }
        }
    }
}