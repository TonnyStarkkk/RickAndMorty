package com.example.aidar_hw_5_2.ui.fragments.character_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.aidar_hw_5_2.data.api.ApiService
import com.example.aidar_hw_5_2.data.model.characters.Character
import com.example.aidar_hw_5_2.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val api: ApiService) : ViewModel() {

    fun getCharacterById(id: Int) : LiveData<Resource<Character>> {
        return liveData {
            emit(Resource.Loading())
            val result = runCatching {
                withContext(Dispatchers.IO) {
                    api.getSingleCharacter(id)
                }
            }
            result.onSuccess { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    emit(Resource.Success(body))
                } else {
                    emit(Resource.Error("Error : ${response.message()}"))
                }
            }.onFailure { exception ->
                emit(Resource.Error("Network error : ${exception.message}"))
            }
        }
    }
}