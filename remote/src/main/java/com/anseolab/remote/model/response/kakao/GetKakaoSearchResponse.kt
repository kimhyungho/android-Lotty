package com.anseolab.remote.model.response.kakao

import com.google.gson.annotations.SerializedName

data class GetKakaoSearchResponse(
    @SerializedName("documents")
    val documents: List<KakaoStoreResponse>
) {
    data class KakaoStoreResponse(
        @SerializedName("id")
        val id: String,
        @SerializedName("address_name")
        val address_name: String,
        @SerializedName("phone")
        val phone: String,
        @SerializedName("place_name")
        val place_name: String,
        @SerializedName("place_url")
        val place_url: String,
        @SerializedName("road_address_name")
        val road_address_name: String,
        @SerializedName("x")
        val x: String,
        @SerializedName("y")
        val y: String
    )
}