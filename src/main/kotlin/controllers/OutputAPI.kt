package controllers

import models.Output
import persistence.Serializer

// Controller for output
class OutputAPI(serializerType: Serializer) {
    private var outputList = ArrayList<Output>()
    private var serializer: Serializer = serializerType

    @Throws(Exception::class)
    fun load() {
        outputList = (serializer.read() as ArrayList<Output>)
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(outputList)
        print("Saving output one\n")
    }

    // Function add output to output list
    fun addOutput(output: Output): Boolean {
        return outputList.add(output)
    }

    // Function number of packed
    fun numberOfPacked(): Int {
        return outputList.size
    }

    /*

        // Function to format list of strings -- to my desired format
        private fun formatListString(outputFormat: List<Output>): String =
            outputFormat
                .joinToString(separator = "\n") { output ->
                    outputList.indexOf(output).toString() + ": " + output.toString() }
     */

    // Function to format a list of strings for musgraves -- variety, type and count
    private fun formatMString(outputFormatM: List<Output>): String =
        outputFormatM
            .joinToString(separator = "\n") { output: Output ->
                "Variety: " + output.variety + " -- Type: " + output.type + " x" + output.count
            }

    // Function to format list of strings -- variety, type and count
    private fun formatVTCString(outputVTC: List<Output>): String =
        outputVTC
            .joinToString(separator = "\n") { output ->
                output.variety + " " + output.type + " " + "x" + output.count
            }

    // Function to list Musgraves output -- format M
    fun listOutputM(): String =
        if (outputList.isEmpty()) {
            "Nothing packed for Musgraves today"
        } else {
            formatMString(outputList)
        }

    // Function to list all eating apples output in VTC format
    fun listEatingOutputVTC(): String =
        if (outputList.isEmpty()) {
            "No apples packed today"
        } else {
            formatVTCString(outputList.filter { output: Output -> output.isEatingApple })
        }

    // Function to list all bramleys output in VTC format
    fun listBramleyOutputVTC(): String =
        if (outputList.isEmpty()) {
            "No bramleys packed"
        } else {
            formatVTCString(outputList.filter { output: Output -> !output.isEatingApple })
        }

// End of OutputAPI class
}
