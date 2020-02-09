package ru.skillbranch.devintensive.ui.custom

import android.graphics.*
import androidx.annotation.ColorInt

class TextToDraw(
    val width: Int,
    val height: Int,
    val text: String = "",
    val textSize: Int = 0,
    @ColorInt val textColor: Int = -0x100000,
    @ColorInt val backgroundColor: Int = -0x100000
) {

    private var bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    fun draw():Bitmap{
        val canvas = Canvas(bitmap)
        canvas.drawColor(backgroundColor)

        if(text.isNotEmpty()){
            drawAva(canvas)
        }

        return bitmap
    }

    private fun drawAva(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = textColor
        paint.textSize = textSize.toFloat()
        paint.textAlign = Paint.Align.CENTER

        val textRect = Rect()
        paint.getTextBounds(text, 0, text.length, textRect)

        val backRect = RectF()
        backRect.set(0f, 0f, width.toFloat(), height.toFloat())

        val bottomText = backRect.centerY() - textRect.exactCenterY()
        canvas.drawText(text, backRect.centerX(), bottomText, paint)
    }
}