package com.harsh.tpf.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harsh.tpf.model.User
import com.harsh.tpf.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor() : ViewModel() {

    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>>
        get() = _users

    val selectedLiveData = MutableLiveData<List<User>>()

    private val formValidatorMutableLiveData = MutableLiveData<Boolean>()
    fun isFormValid(): LiveData<Boolean> = formValidatorMutableLiveData

    fun fetchUsers() {
        viewModelScope.launch {
            _users.postValue(Resource.loading(null))
            _users.postValue(Resource.success(getLocalUser()))
        }
    }

    fun validateForm() {
        var count = 0
        val selectedUser = mutableListOf<User>()
        _users.value?.data?.forEach {
            if (it.isSelected) {
                count++
                selectedUser.add(it)
            }
        }

        selectedLiveData.postValue(selectedUser)

        if (count > 4)
            formValidatorMutableLiveData.postValue(true)
        else
            formValidatorMutableLiveData.postValue(false)
    }

    private fun getLocalUser(): List<User> {
        val mutableList = mutableListOf<User>()
        for (i in 1..8) {
            mutableList.add(User(id = i, name = "Things $i", isSelected = false))
        }
        return mutableList
    }
}