package com.anseolab.lotty.extensions

import android.util.Log
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.*

fun Date.getDrwNum(): Long {
    return ((time - Date(1647085500000).time) / 604800000) + 1006
}

fun LocalDate.getNextSaturday(): LocalDate {
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    if (this.dayOfWeek == DayOfWeek.SATURDAY && currentHour <= 20) {
        if (currentHour == 20 && currentMinute >= 45) {
            return this.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
        }
        return this
    }

    return this.with(TemporalAdjusters.next(DayOfWeek.SATURDAY))
}