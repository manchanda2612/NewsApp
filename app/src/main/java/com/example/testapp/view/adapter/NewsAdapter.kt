package com.example.testapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.base.OnItemClickListener
import com.example.testapp.databinding.RowNewsBinding
import com.example.testapp.model.pojo.ApiResponse

class NewsAdapter(private val list: List<ApiResponse.Article>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding: RowNewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.row_news, viewGroup, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.binding?.article = list[i]
        viewHolder.binding?.listener = listener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding: RowNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: RowNewsBinding? = null

        init {
            this.binding = binding
        }
    }
}