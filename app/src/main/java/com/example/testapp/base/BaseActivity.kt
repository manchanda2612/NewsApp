package com.example.testapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders


abstract class BaseActivity<B : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    protected var viewDataBinding: B? = null
    protected var mViewModel: V? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        viewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        viewDataBinding?.lifecycleOwner = this
        viewDataBinding?.executePendingBindings()
        performDataBinding()
    }

    private fun performDataBinding() {
        this.mViewModel = if (mViewModel == null) ViewModelProviders.of(
            this,
            ViewModelFactory()
        ).get(getViewModelClass()) else mViewModel
        if (getBindingVariable() >= 0)
            viewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        viewDataBinding?.executePendingBindings()
    }

    protected abstract fun getLayout(): Int

    protected abstract fun getBindingVariable(): Int

    protected abstract fun getViewModelClass(): Class<V>
}