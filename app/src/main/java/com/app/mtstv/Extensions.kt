package com.app.mtstv

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageFromUrl(url: String) {
    Glide.with(this)
        .load(url)
        .apply(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        .transition(DrawableTransitionOptions().crossFade())
        .into(this)
}