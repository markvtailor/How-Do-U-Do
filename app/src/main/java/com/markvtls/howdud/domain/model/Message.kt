package com.markvtls.howdud.domain.model

import com.google.firebase.Timestamp

data class Message(
    var author: String,
    var date: Timestamp,
    var isNew: Boolean,
    var text: String
) {
    constructor(): this("",Timestamp(60L, 1),true,"")
}
