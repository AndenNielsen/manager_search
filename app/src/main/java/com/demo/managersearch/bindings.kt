package com.demo.managersearch

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
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

    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(editable: Editable) {
            subject.value = editable.toString()
        }
    })
}