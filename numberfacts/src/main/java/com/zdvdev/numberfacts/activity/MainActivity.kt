package com.zdvdev.numberfacts.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.zdvdev.numberfacts.R
import com.zdvdev.numberfacts.fragment.FactsFragment
import com.zdvdev.numberfacts.widget.CustomTypefaceSpan
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Shyish
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, FactsFragment()).commit()
        }

        val museoTypeface = Typeface.createFromAsset(assets, "fonts/MuseoSansRounded-300.otf")
        val museoBoldTypeface = Typeface.createFromAsset(assets, "fonts/MuseoSansRounded-700.otf")

        val appNameFirst = getString(R.string.app_name_first)
        val appNameSecond = getString(R.string.app_name_second)

        val firstLen = appNameFirst.length
        val secondLen = appNameSecond.length

        val lightBlueColor = ContextCompat.getColor(this, R.color.blue_light)
        val darkBlueColor = ContextCompat.getColor(this, R.color.blue_dark)

        val wordtoSpan = SpannableString(appNameFirst + appNameSecond)
        wordtoSpan.apply {
            setSpan(CustomTypefaceSpan(museoBoldTypeface), 0, firstLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(darkBlueColor), 0, firstLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            //		wordtoSpan.setSpan(new ShadowSpan(10, 0, 0, lightBlueColor), 0, firstLen, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            setSpan(CustomTypefaceSpan(museoTypeface), firstLen, firstLen + secondLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(lightBlueColor), firstLen, firstLen + secondLen, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            //		wordtoSpan.setSpan(new ShadowSpan(60, 0, 0, darkBlueColor), 0, firstLen, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        }

        // Update the action bar title with the TypefaceSpan instance
        setSupportActionBar(toolbar)
        supportActionBar?.title = wordtoSpan
    }
}
