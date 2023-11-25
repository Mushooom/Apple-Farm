// Alexander Novakovsky Apple Farm App
import controllers.AppleBinAPI
import models.AppleBin
import mu.KotlinLogging
import utils.ScannerInput.ScannerInput.readNextInt
import utils.ScannerInput.ScannerInput.readNextLine
import java.io.File
import kotlin.system.exitProcess
import persistence.JSONSerializer

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
        0. exit
    """.trimIndent()
    )
}

// Run menu function
fun runMenu(){
    do {
        when (val option: Int = mainMenu()) {
            1 -> addBin()
            2 -> listAllBins()
           // 0 -> exitApp()
            else -> println("Invalid option")
        }
    } while (true)
}

// Function add bin
fun addBin(){
    logger.info { "Adding new Bin to grading" }
    val batch = readNextLine("Batch: ")
    val variety = readNextLine("Variety: ")
    val isAdded = appleBinAPI.add(AppleBin(batch, variety, isBinFinished = false))

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

