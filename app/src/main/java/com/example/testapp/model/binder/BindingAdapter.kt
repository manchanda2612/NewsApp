package com.example.testapp.model.binder

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


@BindingAdapter("loadImage")
fun AppCompatImageView.loadImage(url: String?) {
    url?.let {
        Picasso.get()
            .load(url)
            .resize(120, 120)
            .placeholder(android.R.drawable.ic_menu_report_image)
            .error(android.R.drawable.ic_menu_report_image)
            .into(this)
    }
}