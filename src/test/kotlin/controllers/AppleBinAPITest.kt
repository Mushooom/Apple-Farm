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

class AppleBinAPITest {
    private var eatingAppleBin: AppleBin? = null
    private var bramleyBin: AppleBin? = null
    private var eatingAppleBinPL: AppleBin? = null
    private var bramleyBinPL: AppleBin? = null
    private var newVarietyBin: AppleBin? = null
    private var addBins: AppleBinAPI? = AppleBinAPI(JSONSerializer(File("bins.json")))
    private var emptyBins: AppleBinAPI? = AppleBinAPI(JSONSerializer(File("bins.json")))

    private var currentTime = LocalDateTime.now()!!
    var time3 = currentTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))!!

    @BeforeEach // Dummy Data -- set values and loads into APP arraylist
    fun setup(){
        eatingAppleBin = AppleBin("28", true, "Red Elstar", time3, time3+15, false )
        bramleyBin = AppleBin("10", false, "Bramley1", time3, time3+15, true)
        eatingAppleBinPL = AppleBin("pljonago", true, "Jonagored", time3, time3+15, true )
        bramleyBinPL = AppleBin("pl1", false, "Bramley2", time3, time3+50, false)
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

    //Tests for adding the bins
    @Nested
    inner class AddBins {
        @Test //Test to add bin to empty arraylist
        fun `adding a bin to empty arraylist`() {
            val newBin = AppleBin("EmptyListBatch", true, "Red", time3, time3+1, false)
            assertEquals(0, actual = emptyBins!!.numberOfBins())
            assertTrue(/* condition = */ emptyBins!!.add(newBin))
            assertEquals(1, emptyBins!!.numberOfBins())
            assertEquals(newBin, emptyBins!!.findBin(index = emptyBins!!.numberOfBins() -1))
            //End of adding to empty arraylist
        }

        @Test //Test to add bin to populated arraylist
        fun `adding a bin to populated arraylist`(){
            val newBin = AppleBin("PopulatedBatch", false, "Bramley", time3, time3+2, true)
            assertEquals(5, addBins!!.numberOfBins())
            assertTrue(/* condition = */ addBins!!.add(newBin))
            assertEquals(6, addBins!!.numberOfBins())
            assertEquals(newBin, addBins!!.findBin(addBins!!.numberOfBins() -1))
        //End of adding to populated arraylist
        }
    //End of Nested AddBins class
    }

    //Test for listing the bins
    @Nested
    inner class ListBins {

        //Test to list from empty arraylist
        @Test
        fun `list all bins from empty arraylist`(){
            assertEquals(0, emptyBins!!.numberOfBins())
            assertTrue(emptyBins!!.listAllBins().contains("No bins"))
        //End list -- empty array
        }

        // Test to list from populated arraylist
        @Test
        fun `list all bins form populated arraylist`(){
            assertEquals(5, addBins!!.numberOfBins())
            val noteBin = addBins!!.listAllBins()
            assertTrue(noteBin.contains("Red Elstar"))
            assertTrue(noteBin.contains("Bramley1"))
            assertTrue(noteBin.contains("Jonagored"))
            assertTrue(noteBin.contains("Bramley2"))
            assertTrue(noteBin.contains("Honey crunch"))
        //End list -- populated array
        }

        // Test to list active bins -- empty arraylist
        @Test
        fun `list all active bins empty arraylist`(){
            assertEquals(0, emptyBins!!.numberOfActiveBins())
            assertTrue(emptyBins!!.listActiveBins().contains("No bins in grading"))
        // End list active bins -- empty array
        }

        // Test to list active bins -- populated arraylist 3 active
        @Test
        fun `list all active bins populated arraylist`(){
            assertEquals(3, addBins!!.numberOfActiveBins())
            val activeBins = addBins!!.listActiveBins()
            assertTrue(activeBins.contains("28"))
            assertFalse(activeBins.contains("10"))
            assertFalse(activeBins.contains("pljonago"))
            assertTrue(activeBins.contains("pl1"))
            assertTrue(activeBins.contains("29"))
        //End list active bins -- populated array 3 active
        }

        // Test to list all finished bins -- empty arraylist
        @Test
        fun `list all finished bins empty arraylist`(){
            assertEquals(0, emptyBins!!.numberOfActiveBins())
            assertTrue(emptyBins!!.listFinishedBins().contains("No bins have been graded"))
        // End list finished -- empty arraylist
        }

        // Test to list all finished bins -- populated array 2 finished
        @Test
        fun `list all finished bins populated arraylist`(){
            assertEquals(2, addBins!!.numberOfFinishedBins())
            val finishedBins = addBins!!.listFinishedBins()
            assertFalse(finishedBins.contains("28"))
            assertTrue(finishedBins.contains("10"))
            assertTrue(finishedBins.contains("pljonago"))
            assertFalse(finishedBins.contains("pl1"))
            assertFalse(finishedBins.contains("29"))
        // End list finished -- populated arraylist
        }

    // End of nested list bins class
    }

    //Test class for counting methods
    @Nested
    inner class CountingMethods {

        // Number of bins -- 5
        @Test
        fun `number of bins`(){
            assertEquals(5, addBins!!.numberOfBins())
            assertEquals(0, emptyBins!!.numberOfBins())
        }

        // Number of active bins -- 3
        @Test
        fun `number of active bins`(){
            assertEquals(3, addBins!!.numberOfActiveBins())
            assertEquals(0, emptyBins!!.numberOfActiveBins())
        }

        // Number of finished bins -- 2
        @Test
        fun `number of finished bins`(){
            assertEquals(2, addBins!!.numberOfFinishedBins())
            assertEquals(0, emptyBins!!.numberOfFinishedBins())
        }

        // Number of finished eating apple bins -- 1
        @Test
        fun `number of finished eating apples bins`(){
            assertEquals(1, addBins!!.numberOfFinishedEatingAppleBins())
            assertEquals(0, emptyBins!!.numberOfFinishedEatingAppleBins())
        }

        // Number of finished bramley bins -- 1
        @Test
        fun `number of finished bramley bins`(){
            assertEquals(1, addBins!!.numberOfFinishedBramleyBins())
            assertEquals(0, emptyBins!!.numberOfFinishedBramleyBins())
        }

    // End nested class counting methods
    }

    //Test class for finish the bin method
    @Nested
    inner class FinishTheBin {

        //Test to finish an active bin -- not existing
        @Test
        fun `finish bin that is not active`(){
            assertFalse(addBins!!.finishBin(6))
            assertFalse(addBins!!.finishBin(-1))
            assertFalse(emptyBins!!.finishBin(0))
        }

        //Test to finish already finished bin
        @Test
        fun `finish bin that has been finished`(){
            assertTrue(addBins!!.findBin(1)!!.isBinFinished)
            assertFalse(addBins!!.finishBin(1))
        }

        //Test to finish bin
        @Test
        fun `finish an active bin`(){
            assertFalse(addBins!!.findBin(0)!!.isBinFinished)
            assertTrue(addBins!!.finishBin(0))
            assertTrue(addBins!!.findBin(0)!!.isBinFinished)
        }

    // End nested class finish bin
    }

//End of Test class
}