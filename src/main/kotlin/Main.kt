// Alexander Novakovsky Apple Farm App
import controllers.AppleBinAPI
import models.AppleBin
import mu.KotlinLogging
import utils.ScannerInput.ScannerInput.readNextInt
import utils.ScannerInput.ScannerInput.readNextLine
import java.io.File
import persistence.JSONSerializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess



// Logger variable
var logger = KotlinLogging.logger{}
var currentTime = LocalDateTime.now()
var time2 = currentTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))


// AppleBinAPI variable
private val appleBinAPI = AppleBinAPI(JSONSerializer(File("bins.json")))

// Main function
fun main() {
    logger.info { "Apple Farm APP started" }
    runMenu()
}

// Main menu function
fun mainMenu(): Int {
    return readNextInt("""
        APPLE FARM APP
        Today: $time2
        
        Main menu:
        1. Input
        2. Output
        3. Show active bin
        4. Count finished bins
        99. Dummy Data
        0. exit
        
        Enter option: 
    """.trimIndent()
    )
}

// Run menu function
fun runMenu(){
    do {
        when (val option: Int = mainMenu()) {
            1 -> runInput()
            2 -> listAllBins()
            3 -> activeBins()
            4 -> println(appleBinAPI.numberOfFinishedBins())
            99 -> dummyData()
            0 -> exitApp()
            else -> println("Invalid option $option")
        }
    } while (true)
}

// Sub menu for Input
fun runInput() {
    do {
        when (val option: Int = inputMenu()) {
            1 -> addBin()
            2 -> listAllBins()
            3 -> println(appleBinAPI.numberOfBins())
            4 -> println(appleBinAPI.numberOfFinishedBins())
            5 -> activeBins()
            6 -> println(appleBinAPI.listFinishedBins())
            7 -> finishBin()
            8 -> timeDifference()
            66 -> runMenu()
            99 -> dummyData()
            0 -> exitApp()
            else -> println("Invalid option: $option")
        }
    } while (true)
}

// Gui for input sub menu
fun inputMenu(): Int {
    return readNextInt(
        """
        APPLE FARM GRADING INPUT
        $time2
        
        Input Menu:
        1. Add bin
        2. List all bins
        3. Count all bins
        4. Count finished bins
        5. List active bins
        6. List finished bins
        7. Finish bin
        8. Time difference
        66. Main menu
        99. Dummy Data
        0. exit
        
        Enter option: 
    """.trimIndent()
    )
}
/*

// Sub menu for Output
fun output(){

}
*/

// Function add bin
fun addBin(){
    logger.info { "Adding new Bin to grading" }
    val batch = readNextLine("Batch: ")
    val isEatingApple = appleBinAPI.isEatingApple()
    fun variety(): String {
        return if (isEatingApple) readNextLine("Variety: ") else "Bramley"
    }
    val isAdded = appleBinAPI.add(AppleBin(batch, isEatingApple, variety(), time2, null, false))

    if (isAdded) {
        println("Bin added")
    } else {
        println("Add failed")
    }
}

// Function to list Bins
fun listAllBins() {
    println(appleBinAPI.listAllBins())
}

// Function to list active bins
fun activeBins(){
    println(appleBinAPI.listActiveBins())
}

// Function exit -- application exit on selecting 0 from menu
fun exitApp(){
    println("App exiting")
    logger.info { "App terminated" }
    exitProcess(0)
}

// Function finish the bin
fun finishBin(){
    // First check if there is an active bin
    activeBins()
    if (appleBinAPI.numberOfActiveBins() > 0) {
        val binToFinish = readNextInt("Enter bin number to finish: ")
        // Pass the index of bin to be finished
        if (appleBinAPI.finishBin(binToFinish)) {
            println("Bin finished")
        } else {
            println("Error")
        }
    }
}


// Function time difference
fun timeDifference(){
    time2
   // println(appleBinAPI.listFinishedBins())
}


// Dummy data
fun dummyData() {
    appleBinAPI.add(AppleBin("27", true, "Red Elstar", time2, null, true))
    appleBinAPI.add(AppleBin("Pl", false, "Bramley", time2, null, true))
}



