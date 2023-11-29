package controllers
import models.Output
import models.OutputPL
import persistence.Serializer

// Controller for output
class OutputAPI(serializerType: Serializer) {
    private var outputList = ArrayList<Output>()
    private var outputPLList = ArrayList<OutputPL>()
    private var serializer: Serializer = serializerType

    @Throws(Exception::class)
    fun load() {
        outputList = (serializer.read() as ArrayList<Output>)
        outputPLList = (serializer.read()) as ArrayList<OutputPL>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(outputList)
        serializer.write(outputPLList)
        print("Saving all\n")
    }

    fun addOutput(output: Output): Boolean {
        return outputList.add(output)
    }

    fun addOutputPL(outputPL: OutputPL): Boolean {
        return outputPLList.add(outputPL)
    }

    // Function to format list of strings -- to my desired format
    private fun formatListString(outputFormat: List<Output>): String =
        outputFormat
            .joinToString(separator = "\n") { output ->
                outputList.indexOf(output).toString() + ": " + output.toString() }

    // Function to format a list of strings for musgraves -- variety, type and count
    private fun formatMString(outputFormatM: List<Output>): String =
        outputFormatM
            .joinToString (separator = "\n") { output: Output ->
               "Variety: " + output.variety +  " -- Type: " + output.type + " x" + output.count
    }

    // Function to format list for Phillip Little -- batch, type, count
    private fun formatPLString(outputFormatPL: List<OutputPL>): String =
        outputFormatPL
            .joinToString (separator = "\n") { outputPL ->
                "Batch: " + outputPL.batch + " " + outputPL.type + " x" + outputPL.count
            }

    // Function to list all output
    fun listOutput(): String =
        if (outputList.isEmpty()) "Nothing packed today"
        else formatListString(outputList)

    // Function to list Phillip Little output -- format PL
    fun listOutputPL():  String =
        if (outputPLList.isEmpty()) "Nothing packed for Phillip today"
        else formatPLString(outputPLList)

    // Function to list Musgraves output -- format M
    fun listOutputM(): String =
        if (outputList.isEmpty()) "Nothing packed for Musgraves today"
        else formatMString(outputList)

    // Function to list: variety type and count
    fun listVTC(): String =
        if (outputList.isEmpty()) "Nothing packed today"
            else formatVTCString(outputList)

    // Function to list all eating apples output in VTC format
    fun listEatingOutputVTC(): String =
        if (outputList.isEmpty()) "No apples packed today"
        else formatVTCString(outputList.filter { output: Output -> output.isEatingApple })

    // Function to list all bramleys output in VTC format
    fun listBramleyOutputVTC(): String =
        if (outputList.isEmpty()) "No bramleys packed"
        else formatVTCString(outputList.filter { output: Output -> !output.isEatingApple  })

    // Function to format list of strings -- variety, type and count
    private fun formatVTCString(outputVTC: List<Output>): String =
        outputVTC
            .joinToString(separator = "\n") { output ->
                output.variety + " " + output.type + " " + "x" + output.count }


}