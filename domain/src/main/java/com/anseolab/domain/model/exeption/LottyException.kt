package com.anseolab.domain.model.exeption

class LottyException : RuntimeException {
    val code: String

    constructor(code: String, description: String) : super(description) {
        this.code = code
    }

    constructor(code: String, cause: Throwable) : super(cause) {
        this.code = code
    }

    companion object Code {
        const val OVER_SEARCH_REQUEST = "HTTP 429 Too Many Requests"
    }
}