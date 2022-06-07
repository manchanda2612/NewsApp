package com.example.testapp.view.fragment

import com.example.testapp.BR
import com.example.testapp.R
import com.example.testapp.base.BaseFragment
import com.example.testapp.databinding.FragmentNotificationsBinding
import com.example.testapp.viewModel.NotificationsViewModel

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding, NotificationsViewModel>() {

    override fun getLayout(): Int {
        return R.layout.fragment_notifications
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModelClass(): Class<NotificationsViewModel> {
        return NotificationsViewModel::class.java
    }

    override fun onInternetUnavailable() {
        TODO("Not yet implemented")
    }
}