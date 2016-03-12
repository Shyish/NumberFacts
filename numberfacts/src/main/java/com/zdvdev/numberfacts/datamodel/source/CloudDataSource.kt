package com.zdvdev.numberfacts.datamodel.source

import com.zdvdev.numberfacts.datamodel.model.ResponseFact

import retrofit.RestAdapter
import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

/**
 * @author Shyish
 */
object CloudDataSource {
    // The base API endpoint.
    private val SERVER_URL = "http://numbersapi.com"

    // Url params
    private const val PARAM_NUMBER = "number"
    private const val PARAM_MONTH = "month"
    private const val PARAM_DAY = "day"

    private const val FORMAT = "?json"

    // API methods
    private const val URI_GET_MATH = "/{$PARAM_NUMBER}/math$FORMAT"
    private const val URI_GET_TRIVIA = "/{$PARAM_NUMBER}/trivia$FORMAT"
    private const val URI_GET_DATE = "/{$PARAM_MONTH}/{$PARAM_DAY}/date$FORMAT"
    private const val URI_GET_DATE_DAYS = "/{$PARAM_NUMBER}/date$FORMAT"
    private const val URI_GET_YEAR = "/{$PARAM_NUMBER}/year$FORMAT"

    private val restAdapter by lazy { RestAdapter.Builder().setEndpoint(SERVER_URL).build() }
    val apiManager by lazy { restAdapter.create(ApiManagerService::class.java) }

    interface ApiManagerService {
        @GET(URI_GET_MATH)
        fun getMathFact(@Path(PARAM_NUMBER) number: String): Observable<ResponseFact>

        @GET(URI_GET_TRIVIA)
        fun getTriviaFact(@Path(PARAM_NUMBER) number: String): Observable<ResponseFact>

        @GET(URI_GET_YEAR)
        fun getYearFact(@Path(PARAM_NUMBER) number: String): Observable<ResponseFact>

        @GET(URI_GET_DATE_DAYS)
        fun getDateDaysFact(@Path(PARAM_NUMBER) number: String): Observable<ResponseFact>

        @GET(URI_GET_DATE)
        fun getDateFact(@Path(PARAM_MONTH) month: String, @Path(PARAM_DAY) day: String): Observable<ResponseFact>
    }
}
