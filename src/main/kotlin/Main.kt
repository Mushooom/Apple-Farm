// Alexander Novakovsky Apple Farm App
import controllers.AppleBinAPI
import models.AppleBin
import controllers.OutputAPI
import models.Output
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
var currentTime = LocalDateTime.now()!!
var time2 = currentTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))!!


// AppleBinAPI variable
private var appleBinAPI = AppleBinAPI(JSONSerializer(File("bins.json")))
private var outputAPI = OutputAPI(JSONSerializer(File("output.json")))

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
        3. Finish bin
        4. Finished eating apple bins
        5. Finished bramley bins
        99. Dummy Data
        0. Exit
        
        Enter option: 
    """.trimIndent()
    )
}

// Run menu function
fun runMenu(){
    do {
        when (val option: Int = mainMenu()) {
            1 -> runInput()
            2 -> runOutput()
            3 -> finishBin()
            4 -> finishedEatingAppleBins()
            5 -> finishedBramleyBins()
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
            3 -> println("All bins count: " + appleBinAPI.numberOfBins())
            4 -> println("Finished bins: " + appleBinAPI.numberOfFinishedBins())
            5 -> activeBins()
            6 -> println(appleBinAPI.listFinishedBins())
            7 -> finishBin()
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
        66. Main menu
        99. Dummy Data
        0. Exit
        
        Enter option: 
    """.trimIndent()
    )
}

// Gui for output sub menu
fun outputMenu(): Int {
    return readNextInt(
        """
            APPLE FARM PACKING
            $time2
            
            Packing Output Menu:
            1. Musgraves eating apples 
            2. Musgraves bramleys
            3. Phillip Little
            4. Add output
            5. List all output
            66. Main menu
            77. Dummy Data
            0. Exit
            
            Enter option:
        """.trimIndent()
    )
}

// Sub menu for Output
fun runOutput(){
    do {
        when (val option: Int = outputMenu()) {
            1 -> println("Musgraves eating")
            2 -> println("Musgraves bramley")
            3 -> println("Phillip little")
            4 -> addOutput()
            5 -> listOutput()
            66 -> runMenu()
            77 -> dummyData()
            0 -> exitApp()
            else -> println("Wrong option $option")
        }
    } while (true)
}

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


// Function add output
fun addOutput(){
    logger.info { "Adding packed products" }
    val isEatingApple = appleBinAPI.isEatingApple()
    val variety = if (isEatingApple) {
        readNextLine("Variety: ")
    } else {
        "Bramley"
    }
/*    fun variety(): String {
        return if (isEatingApple) readNextLine("Variety: ") else "Bramley"
    }*/
    val type = readNextLine("Type: ")
    val count: Int = readNextInt("Add volume: ")
    val adding = outputAPI.addOutput(Output(isEatingApple, variety, type, count))

    if (adding) {
        println("Added")
    } else {
        println("Error")
    }
}

// Function list all output
fun listOutput(){
    println(outputAPI.listOutput())
}
// Function to list Bins
fun listAllBins() {
    println(appleBinAPI.listAllBins())
}

// Function to list active bins
// TODO should be only one at the time!!
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

// Function display finished bramley bins
fun finishedBramleyBins(){
    println("Finished Bramley bins: " + appleBinAPI.numberOfFinishedBramleyBins())
}

// Function to display finished eating apple bins
fun finishedEatingAppleBins(){
    println("Finished eating apple bins: " + appleBinAPI.numberOfFinishedEatingAppleBins())
}

// Dummy data
fun dummyData() {
    appleBinAPI.add(AppleBin("27", true, "Red Elstar", time2, null, true))
    appleBinAPI.add(AppleBin("Pl", false, "Bramley", time2, null, true))
    appleBinAPI.add(AppleBin("Pl", false, "Bramley", time2, null, false))
}



