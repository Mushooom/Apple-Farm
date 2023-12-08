package models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Data class for AppleBin

data class AppleBin(
    var batch: String,          // Could be letters and numbers
    var isEatingApple: Boolean, // True eating apples false Bramley
    var variety: String,        // Only for eating apples. If not eating apples variety = Bramley
    val timeStarted: String = LocalDateTime.now()!!.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))!!,
    var timeFinished: String?,
    var isBinFinished: Boolean
)

// Reference: https://www.baeldung.com/kotlin/current-date-time