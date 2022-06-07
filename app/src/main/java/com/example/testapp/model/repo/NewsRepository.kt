package com.example.testapp.model.repo

import com.example.testapp.BuildConfig
import com.example.testapp.base.BaseRepository
import com.example.testapp.model.api.ApiService
import com.example.testapp.model.pojo.ApiResponse
import kotlinx.coroutines.launch

const val STATUS_OK = "ok"

class NewsRepository(private val apiService: ApiService) : BaseRepository() {

    fun getTopHeadLines(
        onResponse: (ApiResponse) -> Unit,
        onError: ((String) -> Unit)? = null
    ) {
        scope.launch {
            apiService.getTopHeadLines(getQueryMap()).let {
                if (it.isSuccessful) {
                    it.body()?.let { it1 ->
                        if (it1.status == STATUS_OK) {
                            onResponse.invoke(it1)
                        } else {
                            onError?.invoke(it1.status)
                        }
                    }
                } else {
                    onError?.invoke(it.errorBody().toString())
                }
            }
        }
    }

    private fun getQueryMap(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map["country"] = "in"
        map["apiKey"] = BuildConfig.API_KEY
        return map
    }
}