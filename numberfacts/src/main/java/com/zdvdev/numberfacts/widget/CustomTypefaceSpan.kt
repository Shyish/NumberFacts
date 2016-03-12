package com.zdvdev.numberfacts.widget

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

class CustomTypefaceSpan(val newType: Typeface) : TypefaceSpan("") {

    override fun updateDrawState(ds: TextPaint) = applyCustomTypeFace(ds, newType)
    override fun updateMeasureState(paint: TextPaint) = applyCustomTypeFace(paint, newType)

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
        val oldStyle = paint.typeface?.style ?: 0

        val fake = oldStyle and tf.style.inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }
        paint.typeface = tf
    }
}