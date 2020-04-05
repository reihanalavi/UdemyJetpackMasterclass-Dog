package com.reihanalavi.jetpackdogs.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.reihanalavi.jetpackdogs.R
import com.squareup.picasso.Picasso

fun getProgressDrawable(context: Context): CircularProgressDrawable = CircularProgressDrawable(context).apply {
    strokeWidth = 10f
    centerRadius = 50f
    backgroundColor = android.R.color.holo_red_dark

    start()
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.dog)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}