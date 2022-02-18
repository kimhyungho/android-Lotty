package com.anseolab.remote.model.response.dhlottery

import com.google.gson.annotations.SerializedName

data class GetCommonDoResponse(
    @SerializedName("totSellamnt")
    val totSellamnt: Long?,
    @SerializedName("returnValue")
    val returnValue: String,
    @SerializedName("drwNoDate")
    val drwNoDate: String?,
    @SerializedName("firstWinamnt")
    val firstWinamnt: Long?,
    @SerializedName("firstAccumamnt")
    val firstAccumamnt: Long?,
    @SerializedName("drwtNo1")
    val drwtNo1: Int?,
    @SerializedName("drwtNo2")
    val drwtNo2: Int?,
    @SerializedName("drwtNo3")
    val drwtNo3 : Int?,
    @SerializedName("drwtNo4")
    val drwtNo4: Int?,
    @SerializedName("drwtNo5")
    val drwtNo5: Int?,
    @SerializedName("drwtNo6")
    val drwtNo6 : Int?,
    @SerializedName("bnusNo")
    val bnusNo: Int?,
    @SerializedName("firstPrzwnerCo")
    val firstPrzwnerCo: Long?,
    @SerializedName("drwNo")
    val drwNo: Long?
)