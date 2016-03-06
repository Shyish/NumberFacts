package com.zdvdev.numberfacts.datamodel

import android.text.TextUtils
import com.zdvdev.numberfacts.datamodel.model.FactType
import com.zdvdev.numberfacts.datamodel.model.ResponseFact
import com.zdvdev.numberfacts.datamodel.source.CloudDataSource
import rx.Observable

/**
 * @author Shyish
 */
object DataManager {
    private val NUMBER_RANDOM = "random"

    /**
     * Generic method to obtain a fact of any kind for the given number
     * @param number The number (or date) to process
     * @param type Fact type
     */
    fun getFact(type: FactType, number: String = "random"): Observable<ResponseFact> {
        var number = number
        if (TextUtils.isEmpty(number)) number = NUMBER_RANDOM

        val apiManager = CloudDataSource.getApiManager()
        return when (type) {
            FactType.MATH -> apiManager.getMathFact(number)
            FactType.DATE -> if (number.contains("/")) {
                val dateSplit = number.split("\\/")
                apiManager.getDateFact(dateSplit[0], dateSplit[1])
            } else {
                apiManager.getDateDaysFact(number)
            }
            FactType.YEAR -> apiManager.getYearFact(number)
            FactType.TRIVIA -> apiManager.getTriviaFact(number)
        }
    }
}
