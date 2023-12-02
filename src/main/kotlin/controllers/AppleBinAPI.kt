package controllers
import com.sun.org.apache.xpath.internal.operations.Bool
import models.AppleBin
import org.jetbrains.dokka.model.doc.Index
import org.jetbrains.dokka.model.doc.Listing
import persistence.Serializer
import utils.ScannerInput
import utils.ScannerInput.ScannerInput.readNextChar
import utils.ScannerInput.ScannerInput.readNextLine
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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

    // Function to format finished bins to string
    fun formatFinishedBinsString(binsToFormat: List<AppleBin>): String =
        binsToFormat
            .joinToString(separator = "\n") { appleBin ->
                appleBins.indexOf(appleBin)
                    .toString() + ": " + "Batch: " + appleBin.batch + " Time started: " + appleBin.timeStarted + "\n Time Finished: " + appleBin.timeFinished
            }


    // Function format by variety
    fun formatVariety(binsToFormat: List<AppleBin>): String =
        binsToFormat
            .joinToString(separator = "\n") { appleBin ->
                appleBins.indexOf(appleBin).toString() + ": " + "Variety: " + appleBin.variety
            }

    // Function number of bins
    fun numberOfBins(): Int {
        return appleBins.size
    }

    // Function to count all finished bins
    fun numberOfFinishedBins(): Int =
        appleBins.count { appleBin: AppleBin -> appleBin.isBinFinished }

    // Function to count finished bramleys
    fun numberOfFinishedBramleyBins(): Int =
        appleBins.count { appleBin: AppleBin -> appleBin.isBinFinished && !appleBin.isEatingApple }

    // Function to count finished eating apple bins
    fun numberOfFinishedEatingAppleBins(): Int =
        appleBins.count { appleBin: AppleBin -> appleBin.isBinFinished && appleBin.isEatingApple }

    // Function active bins -- should always be only one at the time
    fun numberOfActiveBins(): Int =
        appleBins.count { appleBin: AppleBin -> !appleBin.isBinFinished }

    // Fuction to list all bins in the system
    fun listAllBins(): String =
        if (appleBins.isEmpty()) "\nNo bins\n"
        else formatListString(appleBins)

    // Function to list finished if want to check for variety date etc.
    fun listFinishedBins(): String =
        if (numberOfFinishedBins() == 0) "No bins have been graded"
        else formatFinishedBinsString(appleBins.filter { appleBin: AppleBin -> appleBin.isBinFinished })

    // Function to list active (not finished bins) should be just one at the time
    fun listActiveBins(): String =
        if (numberOfActiveBins() == 0) "No bins in grading"
        else formatListString(appleBins.filter { appleBin: AppleBin -> !appleBin.isBinFinished })


    // Function to check for an eating apple
    fun isEatingApple(): Boolean {
        do {
            val input = readNextChar("Eating apple? Y/N: ")
            if ((input == 'y') || (input == 'Y')) return true else if ((input == 'n') || (input == 'N')) {
                return false
            } else println("Wrong answer")
        } while (true)
    }

    // Function to finish grading bin
    fun finishBin(indexToFinish: Int): Boolean {
        // Check is bin is not finished
        if (isValidIndex(indexToFinish)) {
            var binToFinish = appleBins[indexToFinish]
            if (!binToFinish.isBinFinished) {
                binToFinish.isBinFinished = true
                binToFinish.timeFinished = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                return true
            }
        }
        return false
    }

    // Function to validate an idex of a bin
    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, appleBins)
    }

    // Function to check if there are items in ArrayList
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    // Function to find an idex of a bin
    fun findBin(index: Int): AppleBin? {
        return if (isValidListIndex(index, appleBins)) {
            appleBins[index]
        } else null
    }

    // Fucntion active bin returns Index as string
    fun formatActiveBin(binsToFormat: List<AppleBin>): String =
        binsToFormat
            .joinToString { appleBin ->
                appleBins.indexOf(appleBin).toString()
            }

    fun listOneOnlyActiveBins(): Int =
        formatActiveBin(appleBins.filter { appleBin: AppleBin -> !appleBin.isBinFinished }).toInt()

//End of function AppleBinAPI
}