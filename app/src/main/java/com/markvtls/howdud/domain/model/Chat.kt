package com.markvtls.howdud.domain.model

data class Chat(
    var icon: String,
    var id: String,
    var title: String,
    var lastMessage: String,
    var date: String,
    var newMessages: Int
)
