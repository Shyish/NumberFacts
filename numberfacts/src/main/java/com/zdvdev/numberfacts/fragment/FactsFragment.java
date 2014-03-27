package com.zdvdev.numberfacts.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.zdvdev.numberfacts.R;
import com.zdvdev.numberfacts.async.OnJobStatusChangedListener;
import com.zdvdev.numberfacts.datamodel.DataManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class FactsFragment extends Fragment implements OnJobStatusChangedListener<String> {

	@InjectView(R.id.factsfragment_number_edittext) EditText numberEditText;
	@InjectView(R.id.factsfragment_fact_textview) TextView factTextView;

	public FactsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.facts_fragment_layout, container, false);
		ButterKnife.inject(this, rootView);
		return rootView;
	}

	@Override public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	@OnClick(R.id.factsfragment_getfact_button)
	void onButtonClicked(){
		String number = numberEditText.getText().toString();
		//TODO
		DataManager.getTriviaFact(number, this, this);
	}

	@Override public void onCompleted(String fact) {
		factTextView.setText(fact);
	}

	@Override public void onUpdate(int progress) {}

	@Override public void onError(Throwable throwable) {
		//TODO
		numberEditText.setError(throwable.getMessage());
	}
}