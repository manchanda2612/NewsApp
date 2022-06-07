package com.example.testapp.base

sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null,
    var statusCode: Int = 0,
    var isTradeTokenApi: Boolean = false
) {
    class Success<T>(data: T, statusCode: Int = 0) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(
        throwable: Throwable,
        data: T? = null,
        statusCode: Int = 0,
        msg: String? = "",
        var isTradeCall: Boolean = false
    ) : Resource<T>(data, throwable, statusCode)
}