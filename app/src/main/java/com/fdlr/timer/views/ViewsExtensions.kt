package com.fdlr.timer.views

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.google.android.material.snackbar.Snackbar

inline fun View.snack(
    @StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG,
    block: Snackbar.() -> Unit
) {
    snack(resources.getString(messageRes), length, block)
}

inline fun View.snack(
    message: String,
    length: Int = Snackbar.LENGTH_LONG,
    block: Snackbar.() -> Unit
) {
    val snack = Snackbar.make(this, message, length)
    snack.block()
    snack.show()
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.enableTrue() {
    this.isEnabled = true
}

fun View.enableFalse() {
    this.isEnabled = false
}



fun View.setMargins(
    left: Int = this.marginStart,
    top: Int = this.marginTop,
    right: Int = this.marginEnd,
    bottom: Int = this.marginBottom
) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(left, top, right, bottom)
    }
}

fun Activity.transparentBar(window: Window) {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
}


