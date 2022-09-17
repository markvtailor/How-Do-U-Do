package com.markvtls.howdud.data.dto

data class Friend (
    var id: String,
    var name: String,
    var isUser: Boolean
        ) {
    constructor(): this("","", false)
}