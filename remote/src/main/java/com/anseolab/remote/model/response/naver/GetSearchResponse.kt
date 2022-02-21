package com.anseolab.remote.model.response.naver

import com.google.gson.annotations.SerializedName

data class GetSearchResponse(
    @SerializedName("result")
    val result: Result
) {
    data class Result(
        @SerializedName("place")
        val place: Place

    ) {
        data class Place(
            @SerializedName("list")
            val list: List<Store>
        ) {
            data class Store(
                @SerializedName("id")
                val id: Long?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("address")
                val address: String?,
                @SerializedName("roadAddress")
                val roadAddress: String?,
                @SerializedName("shortAddress")
                val shortAddress: List<String>?,
                @SerializedName("x")
                val x: String?,
                @SerializedName("y")
                val y: String?
            )
        }
    }
}