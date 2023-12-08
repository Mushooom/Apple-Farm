package models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Data class for AppleBin

data class AppleBin(
    var batch: String,
    var isEatingApple: Boolean,
    var variety: String,
    val timeStarted: String = LocalDateTime.now()!!.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))!!,
    var timeFinished: String?,
    var isBinFinished: Boolean,
)

// Reference: https://www.baeldung.com/kotlin/current-date-time
