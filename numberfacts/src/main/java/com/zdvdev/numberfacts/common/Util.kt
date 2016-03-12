package com.zdvdev.numberfacts.common

/**
 * Validate date like mm/dd
 * source: http://stackoverflow.com/questions/17416595/date-validation-in-android
 * @param date date address for validation
 */
fun validateMDDate(date: String): Boolean {
    val dateSplit = date.split("\\/".toRegex(), 2)

    val month: Int
    val day: Int
    try {
        month = Integer.parseInt(dateSplit[0])
        day = Integer.parseInt(dateSplit[1])
    } catch (e: Exception) {
        return false
    }

    return when {
        month > 12 || month < 1 || day > 31 || day < 1 -> false
        day == 31 && (month == 4 || month == 6 || month == 9 || month == 11) -> false // only 1,3,5,7,8,10,12 has 31 days
        month == 2 || month == 2 -> !(day == 30 || day == 31)
        else -> true
    }
}

