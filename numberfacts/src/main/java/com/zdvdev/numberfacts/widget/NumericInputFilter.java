package com.zdvdev.numberfacts.widget;

import android.text.InputFilter;
import android.text.Spanned;

public class NumericInputFilter implements InputFilter {
	public NumericInputFilter() {}

	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		for (int k = start; k < end; k++) {
			if (!Character.isDigit(source.charAt(k))) {
				return "";
			}
		}
		return null;
	}
}