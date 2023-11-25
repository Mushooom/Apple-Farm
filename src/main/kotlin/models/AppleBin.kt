package models



data class AppleBin(
    var isEatingApple: Boolean,
    var batch: String,
    var variety: String,
    val currentDateTime: java.util.Date = java.util.Date(),
    var isFinished: Boolean
)