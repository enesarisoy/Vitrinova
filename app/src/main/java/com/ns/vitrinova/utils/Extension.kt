package com.ns.vitrinova.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.downloadImage(url: String) {
    Glide.with(context).load(url).into(this)
}

