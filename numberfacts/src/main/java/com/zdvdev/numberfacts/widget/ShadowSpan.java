package com.zdvdev.numberfacts.widget;

import android.text.TextPaint;
import android.text.style.CharacterStyle;

public class ShadowSpan extends CharacterStyle {
	public float mDx;
	public float mDy;
	public float mRadius;
	public int mColor;

	public ShadowSpan(float radius, float dx, float dy, int color) {
		this.mRadius = radius;
		this.mDx = dx;
		this.mDy = dy;
		this.mColor = color;
	}

	@Override public void updateDrawState(TextPaint tp) {
		tp.setShadowLayer(mRadius, mDx, mDy, mColor);
	}
}