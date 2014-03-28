package com.zdvdev.numberfacts.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import com.zdvdev.numberfacts.R;
import com.zdvdev.numberfacts.fragment.FactsFragment;
import com.zdvdev.numberfacts.widget.CustomTypefaceSpan;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new FactsFragment()).commit();
		}

		Typeface museoTypeface = Typeface.createFromAsset(getAssets(), "fonts/MuseoSansRounded-300.otf");
		Typeface museoBoldTypeface = Typeface.createFromAsset(getAssets(), "fonts/MuseoSansRounded-700.otf");

		String appNameFirst = getString(R.string.app_name_first);
		String appNameSecond = getString(R.string.app_name_second);

		int firstLen = appNameFirst.length();
		int secondLen = appNameSecond.length();

		int lightBlueColor = getResources().getColor(R.color.blue_light);
		int darkBlueColor = getResources().getColor(R.color.blue_dark);

		Spannable wordtoSpan = new SpannableString(appNameFirst + appNameSecond);
		wordtoSpan.setSpan(new CustomTypefaceSpan(museoBoldTypeface), 0, firstLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		wordtoSpan.setSpan(new ForegroundColorSpan(darkBlueColor), 0, firstLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		wordtoSpan.setSpan(new ShadowSpan(10, 0, 0, lightBlueColor), 0, firstLen, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		wordtoSpan.setSpan(new CustomTypefaceSpan(museoTypeface), firstLen, firstLen + secondLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		wordtoSpan.setSpan(new ForegroundColorSpan(lightBlueColor), firstLen, firstLen + secondLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		wordtoSpan.setSpan(new ShadowSpan(60, 0, 0, darkBlueColor), 0, firstLen, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		// Update the action bar title with the TypefaceSpan instance
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(wordtoSpan);
	}
}
