package android.example.com.matsusmagic.util

import android.content.Context
import android.example.com.matsusmagic.R
import android.view.View
import android.view.inputmethod.InputMethodManager

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

const val BASE_URL = "https://api.scryfall.com/"
const val YT_BASE_URL = "https://www.googleapis.com/youtube/v3/"

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .transform(RoundedCorners(40))
        .error(R.drawable.cardback)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

fun ImageView.loadImageBig(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .error(R.mipmap.ic_najeela)
        .override(Target.SIZE_ORIGINAL)
        .transform(RoundedCorners(40))

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

@BindingAdapter("android:imageurl")
fun loadImage(view: ImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


