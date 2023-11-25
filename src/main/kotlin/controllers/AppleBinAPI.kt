package controllers
import models.AppleBin
import persistence.Serializer
import utils.ScannerInput
import utils.ScannerInput.ScannerInput.readNextChar
import utils.ScannerInput.ScannerInput.readNextLine


// Controller class for AppleBin
class AppleBinAPI(serializerType: Serializer) {
    private var appleBins = ArrayList<AppleBin>()
    private var serializer: Serializer = serializerType

    @Throws(Exception::class)
    fun load() {
        appleBins = (serializer.read() as ArrayList<AppleBin>)
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(appleBins)
        print("Saving all\n")
    }

    // Function to add AppleBin
    fun add(appleBin: AppleBin): Boolean {
        return appleBins.add(appleBin)
    }

    // Function to format list of strings -- to my desired format
    private fun formatListString(binsToFormat: List<AppleBin>): String =
        binsToFormat
            .joinToString(separator = "\n") { appleBin ->
                appleBins.indexOf(appleBin).toString() + ": " + appleBin.toString()
            }

    // Fuction to list all bins in the system
    fun listAllBins(): String =
        if (appleBins.isEmpty()) "\nNo bins\n"
        else formatListString(appleBins)

    // Function number of bins
    fun numberOfBins(): Int {
        return appleBins.size
    }

    // Function finished bins
    fun numberOfFinishedBins(): Int =
        appleBins.count { appleBin: AppleBin -> appleBin.isBinFinished }



    // Function to list active (not finished bins) should be just one at the time
    fun listActiveBins(){

    }

    // Function to check for Eating apple
    fun isEatingApple(): Boolean {
        val input = readNextChar("Eating apple? Y/N: ")
        if ((input == 'y') || (input == 'Y')) {
            return true
        } else if ((input == 'n') || (input == 'N')) {
            return false
        } else println("Wrong answer")
        return TODO("Provide the return value")
    }

}