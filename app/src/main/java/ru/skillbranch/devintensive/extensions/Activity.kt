package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.inputmethod.InputMethodManager
import kotlin.math.roundToInt

fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()?.getWindowToken(), 0)
}

fun Activity.isKeyboardOpen(): Boolean {
    val r = Rect()
    this.window.decorView.getWindowVisibleDisplayFrame(r)
    val winHeight: Int = this.window.decorView.height
    val keyHeight: Int = winHeight - r.bottom
    return keyHeight > 150//при открытой клаве keyHeight 984 при закрытой 144. не лучший метод для определения.
}


fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}
