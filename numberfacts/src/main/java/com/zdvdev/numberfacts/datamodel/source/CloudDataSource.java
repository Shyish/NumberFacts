package com.zdvdev.numberfacts.datamodel.source;

import android.support.v4.app.Fragment;
import com.zdvdev.numberfacts.async.CustomObserver;
import com.zdvdev.numberfacts.async.CustomSubscription;
import com.zdvdev.numberfacts.async.OnJobStatusChangedListener;
import com.zdvdev.numberfacts.async.Subscription;
import com.zdvdev.numberfacts.datamodel.model.ResponseFact;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created with Android Studio.
 *
 * @author Shyish
 *         Date: 27/03/14
 */
public class CloudDataSource {
	// The base API endpoint.
	private static final String SERVER_URL = "http://numbersapi.com";

	// Url params
	static final String PARAM_NUMBER = "number";
	static final String PARAM_MONTH = "month";
	static final String PARAM_DAY = "day";

	static final String FORMAT = "?json";

	// API methods
	static final String URI_GET_MATH = "/{" + PARAM_NUMBER + "}/math" + FORMAT;
	static final String URI_GET_TRIVIA = "/{" + PARAM_NUMBER + "}/trivia" + FORMAT;
	static final String URI_GET_DATE = "/{" + PARAM_MONTH + "}/{" + PARAM_DAY + "}/date" + FORMAT;
	static final String URI_GET_DATE_DAYS = "/{" + PARAM_NUMBER + "}/date" + FORMAT;
	static final String URI_GET_YEAR = "/{" + PARAM_NUMBER + "}/year" + FORMAT;

	//TODO add NOTFOUND options (floor/ceil)?
	//TODO add min/max params?

	private static RestAdapter mRestAdapter;
	private static ApiManagerService mApiManager;

	private static RestAdapter getRestAdapter() {
		if (mRestAdapter == null) {
			mRestAdapter = new RestAdapter.Builder().setServer(SERVER_URL).build();
		}
		return mRestAdapter;
	}

	public static ApiManagerService getApiManager() {
		if (mApiManager == null) {
			mApiManager = getRestAdapter().create(ApiManagerService.class);
		}
		return mApiManager;
	}

	public interface ApiManagerService {
		@GET(URI_GET_MATH) Observable<ResponseFact> getMathFact(@Path(PARAM_NUMBER) String number);

		@GET(URI_GET_TRIVIA) Observable<ResponseFact> getTriviaFact(@Path(PARAM_NUMBER) String number);

		@GET(URI_GET_YEAR) Observable<ResponseFact> getYearFact(@Path(PARAM_NUMBER) String number);

		@GET(URI_GET_DATE_DAYS) Observable<ResponseFact> getDateDaysFact(@Path(PARAM_NUMBER) String number);

		@GET(URI_GET_DATE) Observable<ResponseFact> getDateFact(@Path(PARAM_MONTH) String month, @Path(PARAM_DAY) String day);
	}

	/**
	 * Generic method to make requests
	 *
	 * @param fragment
	 * 		The calling fragment (to avoid responses when the fragment is being dettached/destroyed)
	 * @param listener
	 * 		Response listener
	 * @param sourceObservable
	 * 		The #ApiManagerService method to call
	 */
	public static <T> Subscription requestFromFragmentForObserver(Fragment fragment, OnJobStatusChangedListener<T> listener,
																  Observable<T> sourceObservable) {

		CustomObserver<T> observer = new CustomObserver<T>(listener);
		rx.Subscription subscription;
		Observable<T> ob = (fragment != null) ? AndroidObservable.fromFragment(fragment, sourceObservable) : sourceObservable;

		subscription = ob.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(observer);

		return new CustomSubscription(subscription);
	}

}
