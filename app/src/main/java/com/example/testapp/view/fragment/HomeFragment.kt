package com.example.testapp.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import com.example.testapp.BR
import com.example.testapp.R
import com.example.testapp.base.BaseFragment
import com.example.testapp.base.OnItemClickListener
import com.example.testapp.base.Resource
import com.example.testapp.databinding.FragmentHomeBinding
import com.example.testapp.model.pojo.ApiResponse
import com.example.testapp.view.adapter.NewsAdapter
import com.example.testapp.viewModel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), OnItemClickListener {

    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun onInternetUnavailable() {
        makeText(requireContext(), "Network Connected!", Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel?.getTopHeadLines()
        subscribeObserver()
    }

    private fun subscribeObserver() {
        mViewModel?.apiResponse?.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    val adapter = it.data?.articles?.let { it1 -> NewsAdapter(it1, this) }
                    viewDataBinding?.rvNews?.adapter = adapter
                }
                is Resource.Error -> {

                }
            }
        })
    }

    override fun onItemClick(data: Any) {
        val article = data as ApiResponse.Article
        makeText(requireContext(), article.title, Toast.LENGTH_SHORT).show()
    }
}