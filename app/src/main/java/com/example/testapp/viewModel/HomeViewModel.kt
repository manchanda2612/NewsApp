package com.example.testapp.viewModel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.testapp.base.BaseViewModel
import com.example.testapp.base.Resource
import com.example.testapp.model.pojo.ApiResponse
import com.example.testapp.model.repo.NewsRepository

class HomeViewModel : BaseViewModel() {

    var showLoading: ObservableField<Boolean> = ObservableField(false)
    var apiResponse: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()
    private val repository by lazy {
        api?.let { NewsRepository(it) }
    }

    fun getTopHeadLines() {
        showLoading.set(true)
        repository?.getTopHeadLines({
            showLoading.set(false)
            apiResponse.postValue(Resource.Success(it))
        }, {
            showLoading.set(false)
            apiResponse.postValue(Resource.Error(Throwable("Error"), null))
        })
    }
}