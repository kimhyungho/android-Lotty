package com.anseolab.lotty.mapper

import com.anseolab.domain.model.exeption.LottyException

object ExceptionMapper: Mapper<Throwable, String> {
    override fun mapToView(from: Throwable): String {
        from.printStackTrace()
        return when(from) {
            is LottyException -> {
                when(from.code) {
                    LottyException.Code.OVER_SEARCH_REQUEST -> "금일 검색 횟수를 초과했습니다."
                    else -> "인터넷 연결을 확인해주세요."
                }
            }
            else -> "인터넷 연결을 확인해주세요."
        }
    }
}