package com.markvtls.howdud.data.dto

data class UserChats(
    var chats: List<String>,
) {
    constructor(): this(listOf())
}
