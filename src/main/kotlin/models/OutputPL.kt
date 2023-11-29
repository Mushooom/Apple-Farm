package models

import java.sql.Date

data class OutputPL(
    var batch: String,
    var isEatingApple: Boolean,
    var variety: String,
    var type: String,
    var count: Int,
    var date: String
)
