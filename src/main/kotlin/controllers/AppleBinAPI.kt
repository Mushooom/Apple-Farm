package controllers
import models.AppleBin
import persistence.Serializer


// Controller class for AppleBin
class AppleBinAPI(serializerType: Serializer) {
    private var appleBins = ArrayList<AppleBin>()
    private var serializer: Serializer = serializerType

    @Throws(Exception::class)
    fun load(){
        appleBins = (serializer.read() as ArrayList<AppleBin>)
    }

    @Throws(Exception::class)
    fun store(){
        serializer.write(appleBins)
        print("Saving notes\n")
    }

    // Function to add AppleBin
    fun add(appleBin: AppleBin): Boolean{
        return appleBins.add(appleBin)
    }

    // Function to format list of strings -- to my desired format
    private fun formatListString(binsToFormat: List<AppleBin>) : String =
        binsToFormat
            .joinToString (separator = "\n") { appleBin ->
                appleBins.indexOf(appleBin).toString() + ": " + appleBin.toString() }

    fun listAllBins(): String =
        if (appleBins.isEmpty()) "No bins graded"
        else formatListString(appleBins)

}