package com.zdvdev.numberfacts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.zdvdev.numberfacts.datamodel.model.FactType;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Shyish
 *         Date: 28/03/14
 */
public class SpinnerArrayAdapter extends ArrayAdapter<FactType> {
	private final LayoutInflater mInflater;
	private final int mSpinnerView;
	private final int mSpinnerItemView;

	public SpinnerArrayAdapter(Context context, int spinnerView, int spinnerItemView, FactType[] objects) {
		super(context, spinnerView, objects);
		mInflater = LayoutInflater.from(context);
		mSpinnerView = spinnerView;
		mSpinnerItemView = spinnerItemView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mSpinnerView);
	}

	@Override public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, mSpinnerItemView);
	}

	protected View createViewFromResource(int position, View convertView, ViewGroup parent, int resourceId) {
		// Assuming that the entire view is a TextView
		TextView view;
		if (convertView == null) {

			view = (TextView) mInflater.inflate(resourceId, parent, false);

		} else {
			view = (TextView) convertView;
		}
		final FactType factType = getItem(position);

		view.setText(factType.name());

		return view;
	}
}
