package models


// Data class for AppleBin

data class AppleBin(
  //  var isEatingApple: Boolean, // True eating apples false Bramley
    var batch: String,          // Could be letters and numbers
    var variety: String,        // Only for eating apples. If not eating apples variety = Bramley
  //  val currentDateTime: java.util.Date = java.util.Date(),
    var isBinFinished: Boolean
)