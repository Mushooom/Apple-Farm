// Alexander Novakovsky Apple Farm App
import controllers.AppleBinAPI
import models.AppleBin
import controllers.OutputAPI
import models.Output
import controllers.OutputPLAPI
import models.OutputPL
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
// OutputAPI variable
private var outputAPI = OutputAPI(JSONSerializer(File("output.json")))
// OutputPLAPI variable
private var outputPLAPI = OutputPLAPI(JSONSerializer(File("outputPL.json")))

// Colours
// ref: https://discuss.kotlinlang.org/t/printing-in-colors/22492
const val red = "\u001b[31m"
const val green = "\u001b[32m"
const val resetColour = "\u001b[0m"

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
        11. Save input
        2. Output
        22. Save output
        33. Save all
        34. Load all
        3. Finish bin
        4. Finished eating apple bins
        5. Finished bramley bins
        6. List all packed
        7. One active bin only index
        8. List all bins
        88. Save all
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
            11 -> saveInput()
            2 -> runOutput()
            22 -> saveOutput()
            3 -> finishBin()
            34 -> loadAll()
            4 -> finishedEatingAppleBins()
            5 -> finishedBramleyBins()
            6 -> listOutput()
            7 -> println(listOneActiveOnly())
            8 -> listAllBins()
            88 -> saveAll()
            99 -> dummyData()
            0 -> exitApp()
            else -> println("Invalid option $option")
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
        10. Add auto finish
        11. Save input
        2. List all bins
        22. Load input
        3. Count all bins
        4. Count finished bins
        44. Count unfinished
        5. List active bins
        55. Index of active bin
        6. List finished bins
        7. Finish bin
        66. Main menu
        88. Save all
        99. Dummy Data
        0. Exit
        
        Enter option: 
    """.trimIndent()
    )
}

// Sub menu for Input
fun runInput() {
    do {
        when (val option: Int = inputMenu()) {
            1 -> addAutoFinish()
            11 -> saveInput()
            2 -> listAllBins()
            22 -> loadInput()
            3 -> println("All bins count: " + appleBinAPI.numberOfBins())
            4 -> println("Finished bins: " + appleBinAPI.numberOfFinishedBins())
            44 -> println("Unfinished: " + appleBinAPI.numberOfActiveBins())
            5 -> activeBins()
            55 -> println(listOneActiveOnly())
            6 -> println(appleBinAPI.listFinishedBins())
            7 -> finishBin()
            66 -> runMenu()
            99 -> dummyData()
            0 -> exitApp()
            else -> println("Invalid option: $option")
        }
    } while (true)
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
            4. Add other output
            44. Save output
            5. List all output
            55. Index of one
            6. List Phillip Little -- BTC
            7. List eating apples for musgraves -- VTC
            8. List bramley for musgraves -- VTC
            9. List musgraves all
            55. Load all output
            56. Load musgraves output
            57. Load PL output
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
            1 -> addMusgravesEating()
            2 -> addMusgravesBramley()
            3 -> addPLOutput()
            4 -> addOutput()
            44 -> saveBothOutputs()
            5 -> listOutput()
            6 -> listOutputPL()
            7 -> listEatingApplesVTC()
            8 -> listBramleyVTC()
            9 -> listOutputM()
            55 -> loadBothOutputs()
            56 -> loadOutput()
            57 -> loadOutputPL()
            66 -> runMenu()
            77 -> dummyData77()
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

// Function add output for Phillip Little
fun addPLOutput(){
    logger.info { "Adding PL" }
    val batch = readNextLine("Batch: ")
    val isEatingApple = appleBinAPI.isEatingApple()
    val variety = if (isEatingApple) {
        readNextLine("Variety: ")
    } else {
        "Bramley"
    }
    val type = readNextLine("Type: ")
    val count: Int = readNextInt("Add volume: ")
    val addPLOutput = outputPLAPI.addOutputPL(OutputPL(batch, isEatingApple, variety,type, count, time2))

    if (addPLOutput) {
        println("Added +$count")
    } else {
        println("Error")
    }
}

// Function to add musgraves eating apples
fun addMusgravesEating(){
    logger.info { "Adding musgraves eating apples" }
    val variety = readNextLine("Variety: ")
    val type = readNextLine("Type: ")
    val count: Int = readNextInt("Add volume: ")
    val addingMusgravesEating = outputAPI.addOutput(Output(isEatingApple = true, variety, type, count))

    if (addingMusgravesEating) {
        println("Added: +$count $variety $type ")
    } else {
        println("Error")
    }
}

// Function add musgraves bramley
fun addMusgravesBramley(){
    logger.info { "Adding musgraves bramleys" }
    val variety = "Bramley"
    val type = readNextLine("Type: ")
    val count: Int = readNextInt("Add volume: ")
    val addingMusgravesBramley = outputAPI.addOutput(Output(isEatingApple = false, variety, type, count))

    if (addingMusgravesBramley) {
        println("Added: +$count $type")
    } else {
        println("Error")
    }
}

// Function list all output
fun listOutput(){
    println("Musgraves:")
    println(red + outputAPI.listEatingOutputVTC() + resetColour)
    println(green + outputAPI.listBramleyOutputVTC() + resetColour)
    println("Phillip Little:\n " + outputPLAPI.listOutputPL())
}

// Function list musgraves output - variety type count
fun listOutputM(){
    println(outputAPI.listOutputM())
}

// Function list Phillip Output -- batch type count
fun listOutputPL(){
    println(outputPLAPI.listOutputPL())
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

// Function to list eating apples Output for musgraves -- variety type and count
fun listEatingApplesVTC() {
    println(red + outputAPI.listEatingOutputVTC() + resetColour)
}

// Function to list bramleys Output for musgraves -- VTC format
fun listBramleyVTC(){
    println(green + outputAPI.listBramleyOutputVTC() + resetColour)
}

// Helper function to list oneActiveOnly bin
fun listOneActiveOnly(): Int {
    return (appleBinAPI.listOneOnlyActiveBins()).toInt()
}



// Auto finish


fun quickFinish() {
        if (listOneActiveOnly() < 999999) {
            val binToFinish = listOneActiveOnly()
            // Pass the index of bin to be finished
            if (appleBinAPI.finishBin(binToFinish)) {
                println("Bin finished")
            } else {
                println("Error")
            }
        }
    addBin()
}

fun addAutoFinish() {
    if (appleBinAPI.numberOfActiveBins() == 0) addBin()
    else if (appleBinAPI.numberOfActiveBins() == 1) quickFinish()
}


// Functions save -- input, output, all
fun saveInput(){
    try {
        appleBinAPI.store()
    } catch (e: Exception){
        System.err.println("Error saving input to file: $e")
    }
}

fun saveOutput(){
    try {
        outputAPI.store()
        logger.info { "Saving one" }
    } catch (e: Exception){
        System.err.println("Error saving output to file: $e")
    }
}

fun saveOutputPL(){
    try {
        outputPLAPI.store()
        logger.info { "Saving PL" }
    } catch (e: Exception){
        System.err.println("Error saving PL output to file $e")
    }
}

fun saveBothOutputs(){
    saveOutput()
    saveOutputPL()
}
fun saveAll(){
    saveInput()
    saveOutput()
    saveOutputPL()
}

// Functions load -- input, output, all
fun loadInput(){
    try {
        appleBinAPI.load()
    } catch (e: Exception){
        System.err.println("Error loading from file: $e")
    }
}

fun loadOutput(){
    try {
        outputAPI.load()
    } catch (e: Exception){
        System.err.println("Error loading from file: $e")
    }
}

fun loadOutputPL(){
    try {
        outputPLAPI.load()
    } catch (e: Exception){
        System.err.println("Error loading from file: $e")
    }
}

fun loadBothOutputs(){
    loadOutput()
    loadOutputPL()
}

fun loadAll(){
    loadInput()
    loadOutput()
    loadOutputPL()
}
// Dummy data
fun dummyData() {
    appleBinAPI.add(AppleBin("27", true, "Red Elstar", time2, null, true))
    appleBinAPI.add(AppleBin("Pl", false, "Bramley", time2, null, true))
    appleBinAPI.add(AppleBin("Pl", false, "Bramley", time2, null, false))
}

// Dummy data 77
fun dummyData77(){
    outputAPI.addOutput(Output(true, "Red Elstar", "SV 6pk", 25))
    outputAPI.addOutput(Output(true, "Red Elstar", "CT 4pk", 8 ))
    outputAPI.addOutput(Output(true, "Red Elstar", "Premium loose 50", 10))
    outputAPI.addOutput(Output(true, "Red Elstar", "Bag 8pk", 27))
    outputAPI.addOutput(Output(false, "Bramley", "SV 4pk", 60))
    outputAPI.addOutput(Output(false, "Bramley", "13kg", 15))
    outputAPI.addOutput(Output(false, "Bramley", "13kg Large", 13))
    outputAPI.addOutput(Output(false, "Bramley", "CT 4pk", 20))
    outputPLAPI.addOutputPL(OutputPL("19", true, "Red Elstar", "Count 72", 160, time2))
    outputPLAPI.addOutputPL(OutputPL("19", true, "Red Elstar", "Count 96", 159, time2))
    outputPLAPI.addOutputPL(OutputPL("19", false, "Bramley", "100kg", 9, time2))
}

