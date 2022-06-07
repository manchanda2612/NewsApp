package com.example.testapp.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    /**
     * create Custom ViewModel Classes which will have Different Constructor Parameters.
     *
     * @param T the Type of ViewModel
     * @param modelClass the Model Class
     * @return the Object of ViewModel Class
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            modelClass.getConstructor()
                .newInstance()
        } catch (e: Exception) {
            System.err.print(e.message)
            throw e
        }
    }
}