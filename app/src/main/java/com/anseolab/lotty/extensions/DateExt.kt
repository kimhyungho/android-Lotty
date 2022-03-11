package com.anseolab.lotty.extensions

import android.icu.util.LocaleData
import android.util.Log
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.*

fun Date.getDrwNum(): Long {
    return ((((this.time - 74040000) / 86400000) + 5) / 7) - 1718
}

fun LocalDate.getNextSaturday(): LocalDate {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    if (this.dayOfWeek == DayOfWeek.SATURDAY && currentHour <= 20) {
        if(currentHour == 20 && currentMinute >= 45) {
            return this.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
        }
        return this
    }

    return this.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
}