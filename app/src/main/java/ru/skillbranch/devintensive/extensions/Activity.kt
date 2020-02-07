package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()?.getWindowToken(), 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()
    this.window.decorView.getWindowVisibleDisplayFrame(r)

    val heightDiff: Int = this.window.decorView.height - r.height()
    val check = Math.round(this.convertDpToPx(50F))
    return heightDiff > check
}


fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}