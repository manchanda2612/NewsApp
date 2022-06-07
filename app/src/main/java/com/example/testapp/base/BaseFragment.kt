package com.example.testapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders


abstract class BaseFragment<B : ViewDataBinding, V : BaseViewModel> : Fragment(),
    InternetConnectionListener {

    protected var viewDataBinding: B? = null
    protected var mViewModel: V? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return viewDataBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        viewDataBinding?.lifecycleOwner = this
        viewDataBinding?.executePendingBindings()
        performDataBinding()
        NetworkCommunicator.instance.setInternetConnectionListener(this)
    }

    /**
     * perform DataBinding for the Fragment
     */
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