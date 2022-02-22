package com.anseolab.lotty.extensions

import java.util.*

fun<T: Any> Optional<T>.getOrNull(): T?{
    return if(this.isPresent) this.get() else null
}