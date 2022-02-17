package com.anseolab.lotty.extensions

import java.time.LocalDate
import java.util.*

fun Date.getDrwNum() : Long {
    return ((((this.time - 74040000) / 86400000) + 5) / 7) - 1718
}