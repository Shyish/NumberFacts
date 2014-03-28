package com.zdvdev.numberfacts.datamodel;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.zdvdev.numberfacts.async.OnJobStatusChangedListener;
import com.zdvdev.numberfacts.async.Subscription;
import com.zdvdev.numberfacts.datamodel.model.FactType;
import com.zdvdev.numberfacts.datamodel.model.ResponseFact;
import com.zdvdev.numberfacts.datamodel.source.CacheDataSource;
import com.zdvdev.numberfacts.datamodel.source.CacheDataSource.CachedOnJobFinishedListenerWrapper;
import com.zdvdev.numberfacts.datamodel.source.CloudDataSource;
import rx.Observable;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
@SuppressWarnings("unchecked")
public class DataManager {

	private static final String NUMBER_RANDOM = "random";

	/**
	 * Generic method to obtain a fact of any kind for the given number
	 * @param number The number (or date) to process
	 * @param fragment Calling fragment (for sync stuff)
	 * @param listener The listener to give the result
	 * @param type Fact type
	 * @return A subscription to the call
	 */
	public static Subscription getFact(String number, Fragment fragment, final OnJobStatusChangedListener<ResponseFact> listener, FactType type) {
		if (TextUtils.isEmpty(number)) number = NUMBER_RANDOM;
		String cacheTag = number + type.name();
		ResponseFact fact;

		if (!number.equals(NUMBER_RANDOM) && (fact = (ResponseFact) CacheDataSource.get(cacheTag)) != null) {
			listener.onCompleted(fact);
			return null;
		}

		CachedOnJobFinishedListenerWrapper wrappedListener = new CachedOnJobFinishedListenerWrapper<ResponseFact>(cacheTag, listener);

		Observable<ResponseFact> apiMethod;

		switch (type) {
			case MATH:
				apiMethod = CloudDataSource.getApiManager().getMathFact(number);
				break;
			case DATE:
				if (number.contains("/")) {
					String[] dateSplit = number.split("\\/");
					apiMethod = CloudDataSource.getApiManager().getDateFact(dateSplit[0], dateSplit[1]);
				} else {
					apiMethod = CloudDataSource.getApiManager().getDateDaysFact(number);
				}
				break;
			case YEAR:
				apiMethod = CloudDataSource.getApiManager().getYearFact(number);
				break;
			case TRIVIA:
			default:
				apiMethod = CloudDataSource.getApiManager().getTriviaFact(number);
				break;
		}

		return CloudDataSource.requestFromFragmentForObserver(fragment, wrappedListener, apiMethod);
	}
}
