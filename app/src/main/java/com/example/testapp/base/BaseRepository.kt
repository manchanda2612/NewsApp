package com.example.testapp.base

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

open class BaseRepository {

    protected val scope by lazy { CoroutineScope(Dispatchers.IO + coroutineExceptionHandler) }

    // Cancel the scope to cancel ongoing coroutines work
    fun cancel() {
        scope.cancel()
    }

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            throwable.message?.let { Log.d("Exception = ", it) }
        }
}