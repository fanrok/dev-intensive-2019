package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import ru.skillbranch.devintensive.R

/**
 * Реализуй CustomView с названием класса CircleImageView и кастомными xml атрибутами cv_borderColor (цвет границы (format="color") по умолчанию white) и cv_borderWidth (ширина границы (format="dimension") по умолчанию 2dp). CircleImageView должна превращать установленное изображение в круглое изображение с цветной рамкой, у CircleImageView должны быть реализованы методы @Dimension getBorderWidth():Int, setBorderWidth(@Dimension dp:Int), getBorderColor():Int, setBorderColor(hex:String), setBorderColor(@ColorRes colorId: Int). Используй CircleImageView как ImageView для аватара пользователя (@id/iv_avatar)
 */
class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 2
    }

    private var cv_borderColor = DEFAULT_BORDER_COLOR
    private var cv_borderWidth = DEFAULT_BORDER_WIDTH

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
        cv_borderColor =
            a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
        cv_borderWidth = a.getInt(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDER_WIDTH)
    }

    @Dimension
    fun getBorderWidth(): Int {

        return cv_borderWidth
    }

    fun setBorderWidth(@Dimension dp: Int) {
        cv_borderWidth = dp
    }

    fun getBorderColor(): Int {

        return cv_borderColor
    }

    fun setBorderColor(hex: String) {
        cv_borderWidth = hex.toInt(16)
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        cv_borderWidth = colorId
    }

    override fun onDraw(canvas: Canvas) {
        drawRound(canvas)
        drawStroke(canvas)
    }

    private fun drawStroke(canvas: Canvas) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val radius = width / 2f

        paint.color = cv_borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = cv_borderWidth.toFloat()

        canvas.drawCircle(width/2f, width/2f, radius-cv_borderWidth, paint)
    }

    private fun drawRound(canvas: Canvas) {
        val b: Bitmap = (drawable as BitmapDrawable).bitmap
        val bitmap = b.copy(Bitmap.Config.ARGB_8888, true)

        val scaledBitmap: Bitmap
        val ratio: Float = bitmap.width.toFloat() / bitmap.height.toFloat()
        val height: Int = Math.round(width / ratio)
        scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)

        val shader: Shader
        shader = BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val rect = RectF()
        rect.set(0f, 0f, width.toFloat(), height.toFloat())

        val imagePaint = Paint()
        imagePaint.isAntiAlias = true
        imagePaint.shader = shader
        canvas.drawRoundRect(rect, width.toFloat(), height.toFloat(), imagePaint)
    }
}