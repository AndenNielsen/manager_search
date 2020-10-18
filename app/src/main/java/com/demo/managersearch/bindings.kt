package com.demo.managersearch

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import coil.load
import kotlinx.coroutines.flow.MutableStateFlow

@BindingAdapter("imageUrl")
fun bindImageUri(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
    }
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}

@BindingAdapter("onQueryChange")
fun bindOnTextChange(editText: EditText, subject: MutableStateFlow<String?>) {

    editText.doAfterTextChanged { subject.value = it.toString() }
}