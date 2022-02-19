package com.anseolab.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Lottery(
    val totSellamnt: Long?,
    val returnValue: String,
    val drwNoDate: LocalDate?,
    val firstWinamnt: Long?,
    val firstAccumamnt: Long?,
    val drwtNo1: Int?,
    val drwtNo2: Int?,
    val drwtNo3: Int?,
    val drwtNo4: Int?,
    val drwtNo5: Int?,
    val drwtNo6: Int?,
    val bnusNo: Int?,
    val firstPrzwnerCo: Long?,
    val drwNo: Long?
): Parcelable