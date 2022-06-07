package com.example.testapp.view.fragment

import com.example.testapp.BR
import com.example.testapp.R
import com.example.testapp.base.BaseFragment
import com.example.testapp.databinding.FragmentDashboardBinding
import com.example.testapp.viewModel.DashboardViewModel

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {

    override fun getLayout(): Int {
        return R.layout.fragment_dashboard
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModelClass(): Class<DashboardViewModel> {
        return DashboardViewModel::class.java
    }

    override fun onInternetUnavailable() {
        TODO("Not yet implemented")
    }
}