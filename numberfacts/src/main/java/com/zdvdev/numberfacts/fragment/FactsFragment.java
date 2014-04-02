package com.zdvdev.numberfacts.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.zdvdev.numberfacts.BuildConfig;
import com.zdvdev.numberfacts.R;
import com.zdvdev.numberfacts.async.OnJobStatusChangedListener;
import com.zdvdev.numberfacts.common.Util;
import com.zdvdev.numberfacts.datamodel.DataManager;
import com.zdvdev.numberfacts.datamodel.model.FactType;
import com.zdvdev.numberfacts.datamodel.model.ResponseFact;
import retrofit.RetrofitError;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
public class FactsFragment extends Fragment
		implements OnJobStatusChangedListener<ResponseFact>, TextWatcher, RadioGroup.OnCheckedChangeListener, InputFilter {

	private static final String PLAY_URL = "http://goo.gl/U1GTpx";

	@InjectView(R.id.factsfragment_number_edittext) EditText numberEditText;
	@InjectView(R.id.factsfragment_fact_textview) TextView factTextView;
	@InjectView(R.id.factsfragment_type_group) RadioGroup typeRadioGroup;
	@InjectView(R.id.factsfragment_getfact_button) Button getFactButton;
	@InjectView(R.id.factsfragment_share_button) ImageButton shareButton;

	private FactType currentFact = FactType.TRIVIA;

	public FactsFragment() {}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.facts_fragment_layout, container, false);
		ButterKnife.inject(this, rootView);
		return rootView;
	}

	@Override public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		typeRadioGroup.setOnCheckedChangeListener(this);
		typeRadioGroup.check(typeRadioGroup.getChildAt(0).getId());

		numberEditText.setFilters(new InputFilter[] {this});
		numberEditText.addTextChangedListener(this);
	}

	@OnClick(R.id.factsfragment_getfact_button) void onGetFactButtonClicked() {
		String number = numberEditText.getText().toString();
		if (number.contains("/") && !Util.validateMDDate(number)) {
			numberEditText.setError(getString(R.string.error_date));
		} else {
			DataManager.getFact(number, this, this, currentFact);
		}
	}

	@OnClick(R.id.factsfragment_share_button) void onShareButtonClicked() {
		Intent share = new Intent(android.content.Intent.ACTION_SEND);

		share.setType("text/plain");
		share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Take a look at this fact!");
		share.putExtra(android.content.Intent.EXTRA_TEXT, "Did you know that " + factTextView.getText() + "? (via NumberFacts " + PLAY_URL +
														  ')');

		//TODO try to compress the text for twitter share(?)

		startActivity(Intent.createChooser(share, "Share this fact"));
	}


	/**
	 * ********************
	 * InputFilter METHOD *
	 * ********************
	 */
	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		for (int k = start; k < end; k++) {
			char c = source.charAt(k);
			//Only allows digits or digits plus the '/' char if the current fact type is DATE
			if (!(Character.isDigit(c) || (currentFact == FactType.DATE && c == '/'))) {
				return "";
			}
		}
		return null;
	}

	/**
	 * ************************************
	 * OnJobStatusChangedListener METHODS *
	 * ************************************
	 */
	@Override public void onCompleted(ResponseFact fact) {
		if (!TextUtils.isEmpty(fact.getText())) {
			factTextView.setText(fact.getText());
			shareButton.setVisibility(View.VISIBLE);
		}
	}

	@Override public void onError(Throwable throwable) {
		factTextView.setText("");
		shareButton.setVisibility(View.INVISIBLE);
		if (throwable instanceof RetrofitError && ((RetrofitError) throwable).isNetworkError()) {
			Toast.makeText(getActivity(), getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
		} else if (BuildConfig.DEBUG) {
			numberEditText.setError(throwable.getMessage());
		}
	}

	@Override public void onUpdate(int progress) {}

	/**
	 * *********************************
	 * OnCheckedChangeListener METHODS *
	 * *********************************
	 */
	@Override public void onCheckedChanged(RadioGroup group, int checkedId) {
		// Clear the edittext to avoid invalid chars in other fact types.
		if (numberEditText.getText().toString().contains("/")) {
			factTextView.setText("");
			numberEditText.setText("");
		}
		int radioButtonID = group.getCheckedRadioButtonId();
		View radioButton = group.findViewById(radioButtonID);
		currentFact = FactType.values()[group.indexOfChild(radioButton)];

		if (currentFact == FactType.DATE) {
			numberEditText.setHint(R.string.write_number_date_hint);
			numberEditText.setInputType(InputType.TYPE_CLASS_TEXT);
		} else {
			numberEditText.setHint(R.string.write_number_hint);
			numberEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		}
	}

	/**
	 * *********************
	 * TextWatcher METHODS *
	 * *********************
	 */
	@Override public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (s.length() > 0) {
			getFactButton.setText(R.string.get_fact);
		} else {
			getFactButton.setText(R.string.random_fact);
		}
	}

	@Override public void afterTextChanged(Editable s) {}

	@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

}