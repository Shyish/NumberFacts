package com.zdvdev.numberfacts.widget

import android.text.TextPaint
import android.text.style.CharacterStyle

class ShadowSpan(var radius: Float, var dx: Float, var dy: Float, var color: Int) : CharacterStyle() {
    override fun updateDrawState(tp: TextPaint) = tp.setShadowLayer(radius, dx, dy, color)
}