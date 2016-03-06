package com.zdvdev.numberfacts.datamodel.source;

import com.zdvdev.numberfacts.datamodel.model.ResponseFact;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

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

    private static RestAdapter restAdapter;
    private static ApiManagerService apiManager;

    private static RestAdapter getRestAdapter() {
        if (restAdapter == null) {
            restAdapter = new RestAdapter.Builder().setEndpoint(SERVER_URL).build();
        }
        return restAdapter;
    }

    public static ApiManagerService getApiManager() {
        if (apiManager == null) {
            apiManager = getRestAdapter().create(ApiManagerService.class);
        }
        return apiManager;
    }

    public interface ApiManagerService {
        @GET(URI_GET_MATH)
        Observable<ResponseFact> getMathFact(@Path(PARAM_NUMBER) String number);

        @GET(URI_GET_TRIVIA)
        Observable<ResponseFact> getTriviaFact(@Path(PARAM_NUMBER) String number);

        @GET(URI_GET_YEAR)
        Observable<ResponseFact> getYearFact(@Path(PARAM_NUMBER) String number);

        @GET(URI_GET_DATE_DAYS)
        Observable<ResponseFact> getDateDaysFact(@Path(PARAM_NUMBER) String number);

        @GET(URI_GET_DATE)
        Observable<ResponseFact> getDateFact(@Path(PARAM_MONTH) String month, @Path(PARAM_DAY) String day);
    }
}
