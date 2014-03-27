package com.zdvdev.numberfacts.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.zdvdev.numberfacts.R;
import com.zdvdev.numberfacts.fragment.FactsFragment;

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
	}
}
