package com.markvtls.howdud.domain.model

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.TextStyle
import java.util.*

data class Chat(
    var icon: String,
    var id: String,
    var title: String,
    var lastMessage: String,
    var date: String,
    var newMessages: Int
)


fun Date.lastMessageDate() : String {
    val date = this.toInstant()
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val messageDate = LocalDateTime.ofEpochSecond(date.epochSecond, date.nano, ZoneOffset.ofHours(3))
    val messageWasNewAt = messageDate.format(formatter)
    val willBeOldAt = messageDate.plusDays(7)

    val currentDate = LocalDateTime.now()

    var result =
    if (messageWasNewAt == currentDate.format(formatter)) {
        messageDate.format(DateTimeFormatter.ofPattern("HH:mm"))
    } else if (currentDate.isBefore(willBeOldAt)) {
        messageDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru","RU")).replaceFirstChar { it.uppercase() }
    } else {
        messageDate.format(formatter)
    }

    return result
}
