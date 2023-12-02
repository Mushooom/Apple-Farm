package controllers

//Imports
import models.AppleBin
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

var currentTime = LocalDateTime.now()!!
var time3 = currentTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))!!


class AppleBinAPITest {
    private var eatingAppleBin: AppleBin? = null
    private var bramleyBin: AppleBin? = null
    private var eatingAppleBinPL: AppleBin? = null
    private var bramleyBinPL: AppleBin? = null
    private var newVarietyBin: AppleBin? = null
    private var addBins: AppleBinAPI? = AppleBinAPI(JSONSerializer(File("bins.json")))
    private var emptyBins: AppleBinAPI? = AppleBinAPI(JSONSerializer(File("bins.json")))

    @BeforeEach // Dummy Data -- set values and loads into APP arraylist
    fun setup(){
        eatingAppleBin = AppleBin("28", true, "Red Elstar", time3, time3+15, false )
        bramleyBin = AppleBin("10", false, "Bramley", time3, time3+15, true)
        eatingAppleBinPL = AppleBin("pl", false, "Jonagored", time3, time3+15, true )
        bramleyBinPL = AppleBin("pl", false, "Bramley", time3, time3+50, false)
        newVarietyBin = AppleBin("29", true, "Honey crunch", time3, time3+60, false)

        // Adding the AppleBin values to array list
        addBins!!.add(eatingAppleBin!!)
        addBins!!.add(bramleyBin!!)
        addBins!!.add(eatingAppleBinPL!!)
        addBins!!.add(bramleyBinPL!!)
        addBins!!.add(newVarietyBin!!)
    //End of setup
    }

    @AfterEach // Delete all the values after each test -- clean the arraylist
    fun tearDown(){
        eatingAppleBin = null
        bramleyBin = null
        eatingAppleBinPL = null
        bramleyBinPL = null
        newVarietyBin = null
        addBins = null
        emptyBins =null
    //End of tearDown
    }

    //Test for adding the bins
    @Nested
    inner class AddBins {
        @Test // Test to add bin to empty arraylist
        fun `adding a bin to empty arraylist`() {
            val newBin = AppleBin("EmptyListBatch", true, "Red", time3, time3+1, false)
            assertEquals(0, actual = emptyBins!!.numberOfBins())
            assertTrue(/* condition = */ emptyBins!!.add(newBin))
            assertEquals(1, emptyBins!!.numberOfBins())
            assertEquals(newBin, emptyBins!!.findBin(index = emptyBins!!.numberOfBins() -1))
        // End of adding to empty arraylist
        }

        @Test // Test to add bin to populated arraylist
        fun `adding a bin to populated arraylist`(){
            val newBin = AppleBin("PopulatedBatch", false, "Bramley", time3, time3+2, true)
            assertEquals(5, addBins!!.numberOfBins())
            assertTrue(/* condition = */ addBins!!.add(newBin))
            assertEquals(6, addBins!!.numberOfBins())
            assertEquals(newBin, addBins!!.findBin(addBins!!.numberOfBins() -1))
        // End of adding to populated arraylist
        }
    // End of AddBins class
    }



//End of Test class
}