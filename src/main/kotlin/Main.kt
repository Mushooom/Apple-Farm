// Alexander Novakovsky Apple Farm App
import controllers.AppleBinAPI
import models.AppleBin
import mu.KotlinLogging
import utils.ScannerInput.ScannerInput.readNextInt
import utils.ScannerInput.ScannerInput.readNextLine
import java.io.File
import persistence.JSONSerializer
import java.time.LocalDateTime

// Logger variable
var logger = KotlinLogging.logger{}

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
        
        Main menu:
        1. add bin
        2. list bins
        3. number of bins
        4. finished bins
        0. exit
        
        Enter option: 
    """.trimIndent()
    )
}

// Run menu function
fun runMenu(){
    do {
        when (val option: Int = mainMenu()) {
            1 -> addBin()
            2 -> listAllBins()
            3 -> println(appleBinAPI.numberOfBins())
            4 -> println(appleBinAPI.numberOfFinishedBins())
           // 0 -> exitApp()
            else -> println("Invalid option")
        }
    } while (true)
}

// Function add bin
fun addBin(){
    logger.info { "Adding new Bin to grading" }
    val batch = readNextLine("Batch: ")
    val isEatingApple = appleBinAPI.isEatingApple()
    fun variety(): String {
        return if (isEatingApple == true) readNextLine("Variety: ") else "Bramley"
    }
    val time = java.util.Date()
    val isAdded = appleBinAPI.add(AppleBin(batch, isEatingApple, variety(), time, isBinFinished = false))

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

