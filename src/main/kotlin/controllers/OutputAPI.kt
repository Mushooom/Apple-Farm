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
        print("Saving all\n")
    }

    fun addOutput(output: Output): Boolean {
        return outputList.add(output)
    }

    // Function to format list of strings -- to my desired format
    private fun formatListString(outputFormat: List<Output>): String =
        outputFormat
            .joinToString(separator = "\n") { output ->
                outputList.indexOf(output).toString() + ": " + output.toString() }

    // Function to list all output
    fun listOutput(): String =
        if (outputList.isEmpty()) "Nothing packed today"
        else formatListString(outputList)


}