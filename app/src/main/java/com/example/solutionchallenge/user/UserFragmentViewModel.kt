package com.example.solutionchallenge.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.solutionchallenge.R
import com.example.solutionchallenge.api.Item
import com.example.solutionchallenge.extensions.handleThrowable
import com.example.solutionchallenge.repository.UsersRepository
import com.example.solutionchallenge.repository.UsersRepositoryImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserFragmentViewModel(private val repository: UsersRepository) : ViewModel() {

    private val _uiState = MutableLiveData<UserUiState>()
    val uiState: LiveData<UserUiState> = _uiState

    var userPage = 1
    var userItemResponse: MutableList<Item>? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        val errorPair = exception.handleThrowable()
        _uiState.postValue(UserUiState.Error(title = errorPair.first,message = errorPair.second))
    }

    fun resetState(){
        if(userPage != 1 && userItemResponse != null){
            userPage = 1
            userItemResponse = null
        }
    }

    fun loadUsers(query: String,perPage: Int){
        _uiState.postValue(UserUiState.Loading)
        viewModelScope.launch(exceptionHandler){
            withContext(Dispatchers.IO){
                val response = repository.getUsers(query,perPage,userPage)
                if(response.items.isEmpty()){
                    _uiState.postValue(UserUiState.EmptyList)
                }else{
                    userPage++
                    if(userItemResponse == null){
                        userItemResponse = response.items
                    }else{
                        val oldItems = userItemResponse
                        val newItems = response.items
                        oldItems?.addAll(newItems)
                    }
                    _uiState.postValue(UserUiState.Success(userItemResponse ?: response.items))
                }

            }
        }
    }
}

sealed class UserUiState{
  object Loading: UserUiState()
  object EmptyList: UserUiState()
  data class Success(val items: MutableList<Item>): UserUiState()
  data class Error(val title: Int = R.string.something_went_wrong, val message: Int): UserUiState()
}