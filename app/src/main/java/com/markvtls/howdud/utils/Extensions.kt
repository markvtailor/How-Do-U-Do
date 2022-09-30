package com.markvtls.howdud.utils

import java.text.SimpleDateFormat


fun String.parseNumber(): String {
    val number = this.replace("\\+|\\-|\\s".toRegex(),"")
    return number.replaceFirst("8","7") //fix
}

fun java.util.Date.parseDateTime(): String {
    return SimpleDateFormat("HH:mm").format(this)
}