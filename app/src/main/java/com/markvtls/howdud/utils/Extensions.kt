package com.markvtls.howdud.utils



fun String.parseNumber(): String {
    val number = this.replace("\\+|\\-|\\s".toRegex(),"")
    return number.replaceFirst("8","7") //fix
}