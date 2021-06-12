package com.example.fogo.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.fogo.data.model.CategoryRoom
import com.makeramen.roundedimageview.RoundedImageView


    @BindingAdapter("image")
    fun RoundedImageView.abc(item: CategoryRoom) {
        setImageResource(item.getIconResID())
    }
