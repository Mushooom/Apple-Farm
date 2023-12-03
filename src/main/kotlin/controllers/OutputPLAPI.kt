package controllers

//Imports
import models.OutputPL
import persistence.Serializer

// Controller for OutputPL
class OutputPLAPI (serializerType: Serializer){
    private var outputPLList = ArrayList<OutputPL>()
    private var serializer: Serializer = serializerType

    @Throws(Exception::class)
    fun load() {
        outputPLList = (serializer.read() as ArrayList<OutputPL>)
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(outputPLList)
        print("Saving output PL\n")
    }

    // Function to add output to arraylist PL
    fun addOutputPL(outputPL: OutputPL): Boolean {
        return outputPLList.add(outputPL)
    }

    // Function to format list for Phillip Little -- batch, type, count
    private fun formatPLString(outputFormatPL: List<OutputPL>): String =
        outputFormatPL
            .joinToString (separator = "\n") { outputPL ->
                "Batch: " + outputPL.batch + " " + outputPL.type + " x" + outputPL.count
            }

    // Function to list Phillip Little output -- format PL
    fun listOutputPL():  String =
        if (outputPLList.isEmpty()) "Nothing packed for Phillip today"
        else formatPLString(outputPLList)

    // Function to count Phillip Litlle output
    fun numberOfOutputPL(): Int {
        return outputPLList.size
    }

// End of outputPLAPI class
}