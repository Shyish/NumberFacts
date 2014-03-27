package com.zdvdev.numberfacts.datamodel;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.zdvdev.numberfacts.async.OnJobStatusChangedListener;
import com.zdvdev.numberfacts.async.Subscription;
import com.zdvdev.numberfacts.datamodel.source.CacheDataSource;
import com.zdvdev.numberfacts.datamodel.source.CacheDataSource.CachedOnJobFinishedListenerWrapper;
import com.zdvdev.numberfacts.datamodel.source.CloudDataSource;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
@SuppressWarnings("unchecked")
public class DataManager {

	private static final String MATH_CACHE_SUFFIX = "_math";
	private static final String TRIVIA_CACHE_SUFFIX = "_trivia";
	private static final String YEAR_CACHE_SUFFIX = "_year";
	private static final String DATE_DAYS_CACHE_SUFFIX = "_date_days";
	private static final String DATE_CACHE_SUFFIX = "_date";

	private static final String NUMBER_RANDOM = "random";

	public static Subscription getMathFact(String number, Fragment fragment, final OnJobStatusChangedListener<String> listener) {
		if (TextUtils.isEmpty(number)) number = NUMBER_RANDOM;
		String cacheTag = number + MATH_CACHE_SUFFIX;
		String fact;

		if ((fact = (String) CacheDataSource.get(cacheTag)) != null) {
			listener.onCompleted(fact);
			return null;
		}

		CachedOnJobFinishedListenerWrapper wrappedListener = new CachedOnJobFinishedListenerWrapper<String>(cacheTag, listener);

		return CloudDataSource.requestFromFragmentForObserver(fragment, wrappedListener, CloudDataSource.getApiManager().getMathFact(number));
	}

	public static Subscription getTriviaFact(String number, Fragment fragment, final OnJobStatusChangedListener<String> listener) {
		if (TextUtils.isEmpty(number)) number = NUMBER_RANDOM;
		String cacheTag = number + TRIVIA_CACHE_SUFFIX;
		String fact;

		if ((fact = (String) CacheDataSource.get(cacheTag)) != null) {
			listener.onCompleted(fact);
			return null;
		}

		CachedOnJobFinishedListenerWrapper wrappedListener = new CachedOnJobFinishedListenerWrapper<String>(cacheTag, listener);

		return CloudDataSource.requestFromFragmentForObserver(fragment, wrappedListener, CloudDataSource.getApiManager().getTriviaFact(number));
	}

	public static Subscription getYearFact(String number, Fragment fragment, final OnJobStatusChangedListener<String> listener) {
		if (TextUtils.isEmpty(number)) number = NUMBER_RANDOM;
		String cacheTag = number + YEAR_CACHE_SUFFIX;
		String fact;

		if ((fact = (String) CacheDataSource.get(cacheTag)) != null) {
			listener.onCompleted(fact);
			return null;
		}

		CachedOnJobFinishedListenerWrapper wrappedListener = new CachedOnJobFinishedListenerWrapper<String>(cacheTag, listener);

		return CloudDataSource.requestFromFragmentForObserver(fragment, wrappedListener, CloudDataSource.getApiManager().getYearFact(number));
	}

	public static Subscription getDateDaysFact(String number, Fragment fragment, final OnJobStatusChangedListener<String> listener) {
		if (TextUtils.isEmpty(number)) number = NUMBER_RANDOM;
		String cacheTag = number + DATE_DAYS_CACHE_SUFFIX;
		String fact;

		if ((fact = (String) CacheDataSource.get(cacheTag)) != null) {
			listener.onCompleted(fact);
			return null;
		}

		CachedOnJobFinishedListenerWrapper wrappedListener = new CachedOnJobFinishedListenerWrapper<String>(cacheTag, listener);

		return CloudDataSource.requestFromFragmentForObserver(fragment, wrappedListener, CloudDataSource.getApiManager().getDateDaysFact(number));
	}
	public static Subscription getDateFact(Integer month, Integer day, Fragment fragment, final OnJobStatusChangedListener<String> listener) {
		String cacheTag = month + "/" + day + DATE_CACHE_SUFFIX;
		String fact;

		if ((fact = (String) CacheDataSource.get(cacheTag)) != null) {
			listener.onCompleted(fact);
			return null;
		}

		CachedOnJobFinishedListenerWrapper wrappedListener = new CachedOnJobFinishedListenerWrapper<String>(cacheTag, listener);

		return CloudDataSource.requestFromFragmentForObserver(fragment, wrappedListener, CloudDataSource.getApiManager().getDateFact(month, day));
	}
}
