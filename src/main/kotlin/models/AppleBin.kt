package models

// Data class for AppleBin

data class AppleBin(
    var batch: String,
    var isEatingApple: Boolean,
    var variety: String,
    val timeStarted: String,
    var timeFinished: String?,
    var isBinFinished: Boolean,
)

// Reference: https://www.baeldung.com/kotlin/current-date-time
