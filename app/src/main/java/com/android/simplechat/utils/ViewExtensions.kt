package com.android.simplechat.utils

import android.view.View

fun View.dp2px(dp: Float): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun View.px2dp(px: Int): Float {
    val scale: Float = context.resources.displayMetrics.density
    return (px * scale + 0.5f)
}
