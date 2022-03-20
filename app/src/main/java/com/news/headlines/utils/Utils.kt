package com.news.headlines.utils

import android.app.Activity
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.news.headlines.R
import java.text.SimpleDateFormat
import java.util.*

const val  API_KEY = "cf30654b9cb04c83a0f3276e5e02c904"
const val  DETAILS = "news_detail"
// -------- Progress Bar -----//

fun Activity.hideProgressBar() {
    ProgressUtil.hideLoading()
}
fun Activity.showProgressBar() {
    ProgressUtil.showLoading(this)
}

fun Context.getProgressDrawable(): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(this)
    circularProgressDrawable.strokeWidth = 10f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.progressRotation = 0.8f
    circularProgressDrawable.setColorSchemeColors(ResourcesCompat.getColor(this.resources,
        R.color.white,
        null))
    return circularProgressDrawable
}

fun Context.timeConverter(dateStr:String):String{
    val df = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    df.setTimeZone(TimeZone.getTimeZone("UTC"))
    val date: Date = df.parse(dateStr)
    df.setTimeZone(TimeZone.getDefault())
    val formattedDate: String = df.format(date)
    return formattedDate
}