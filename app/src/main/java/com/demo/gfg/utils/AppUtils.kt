package com.demo.gfg.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {
    companion object {
        fun changeDateFormat(time: String?): String? {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val outputPattern = "MMM d, yyyy h:mm aaa"
            var date: Date?
            var str: String? = null
            try {
                date = SimpleDateFormat(inputPattern).parse(time)
                str = SimpleDateFormat(outputPattern).format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }
    }
}