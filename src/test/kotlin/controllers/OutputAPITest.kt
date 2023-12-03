package controllers

//Imports
import models.Output
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.JSONSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class OutputAPITest {
    private var sEating: Output? = null
    private var sBramley: Output? = null
    private var cEating: Output? = null
    private var cBramley: Output? = null
    private var looseEating: Output? = null
    private var looseBramley: Output? = null
    private var looseBramleyLarge: Output? = null
    private var addOutput: OutputAPI? = OutputAPI(JSONSerializer(File("output.json")))
    private var emptyOutput: OutputAPI? = OutputAPI(JSONSerializer(File("output.json")))

    @BeforeEach // Dummy Data -- set values and load into APP arraylist -- 7
    fun setup(){
        sEating = Output(true, "Red Elstar", "6packSV", 50)
        sBramley = Output(false, "Bramley", "4packSV", 25)
        cEating = Output(true, "Jona gold", "4eatingCT", 15)
        cBramley = Output(false, "Bramley", "4bramleyCT", 5)
        looseEating = Output(true, "Red Elstar", "Premium 50", 10)
        looseBramley = Output(false, "Bramley", "Loose 13kg", 13)
        looseBramleyLarge = Output(false, "Bramley", "Loose large", 7)

        // Adding the output values to array list -- 7
        addOutput!!.addOutput(sEating!!)
        addOutput!!.addOutput(sBramley!!)
        addOutput!!.addOutput(cEating!!)
        addOutput!!.addOutput(cBramley!!)
        addOutput!!.addOutput(looseEating!!)
        addOutput!!.addOutput(looseBramley!!)
        addOutput!!.addOutput(looseBramleyLarge!!)
    //End of setup
    }

    @AfterEach // Delete all values after each test -- clean the arraylist
    fun tearDown(){
        sEating = null
        sBramley = null
        cEating = null
        cBramley = null
        looseEating = null
        looseBramley = null
        looseBramleyLarge = null
    //End of tearDown
    }

    //Test for adding the output
    @Nested
    inner class AddOutput {

        @Test //Test to add output to empty arraylist
        fun `adding output to empty arraylist`(){
            val newOutput = Output(true, "Empty array", "6pkSV", 45)
            assertEquals(0, emptyOutput!!.numberOfPacked())
            assertTrue(/* condition = */ emptyOutput!!.addOutput(newOutput))
            assertEquals(1, emptyOutput!!.numberOfPacked())
        //End of adding to empty arraylist
        }

        @Test //Test to add output to populated arraylist
        fun `adding output to populated arraylist`(){
            val newOutput = Output(false, "Populated array", "4pkCT", 15)
            assertEquals(7, addOutput!!.numberOfPacked())
            assertTrue(/* condition = */ addOutput!!.addOutput(newOutput))
            assertEquals(8, addOutput!!.numberOfPacked())
        //End of adding to populated arraylist
        }

    //End of Nested AddOutput class
    }

    //Test class for listing the output
    @Nested
    inner class ListOutput {

        //Test to list all from empty arraylist
        @Test
        fun `list all output from empty arraylist`(){
            assertEquals(0, emptyOutput!!.numberOfPacked())
            assertTrue(emptyOutput!!.listOutputM().contains("Nothing packed for Musgraves today"))
        //End list all -- empty array
        }

        //Test to list all from populated arraylist - 7
        @Test
        fun `list all output from populated arraylist`(){
            assertEquals(7, addOutput!!.numberOfPacked())
            val noteOutput = addOutput!!.listOutputM()
            assertTrue(noteOutput.contains("6packSV"))
            assertTrue(noteOutput.contains("4packSV"))
            assertTrue(noteOutput.contains("4eatingCT"))
            assertTrue(noteOutput.contains("4bramleyCT"))
            assertTrue(noteOutput.contains("Premium 50"))
            assertTrue(noteOutput.contains("Loose 13kg"))
            assertTrue(noteOutput.contains("Loose large"))
        //End list all -- populated arraylist
        }

        //Test to list eating apples from empty arraylist
        @Test
        fun `list all eating apples from empty arraylist`(){
            assertEquals(0, emptyOutput!!.numberOfPacked())
            assertTrue(emptyOutput!!.listEatingOutputVTC().contains("No apples packed today"))
        //End list eating apples -- empty arraylist
        }

        //Test list eating apples from populated arraylist - 3
        @Test
        fun `list all eating apples from populated arraylist`(){
            assertEquals(7, addOutput!!.numberOfPacked())
            val noteOutput = addOutput!!.listEatingOutputVTC()
            assertTrue(noteOutput.contains("6packSV"))
            assertFalse(noteOutput.contains("4packSV"))
            assertTrue(noteOutput.contains("4eatingCT"))
            assertFalse(noteOutput.contains("4bramleyCT"))
            assertTrue(noteOutput.contains("Premium 50"))
            assertFalse(noteOutput.contains("Loose 13kg"))
            assertFalse(noteOutput.contains("Loose large"))
        //End list eating apples -- populated arraylist
        }

        //Test list bramleys form empty arraylist
        @Test
        fun `list all bramleys from empty arraylist`(){
            assertEquals(0, emptyOutput!!.numberOfPacked())
            assertTrue(emptyOutput!!.listBramleyOutputVTC().contains("No bramleys packed"))
        //End list bramleys -- empty arraylist
        }

        //Test list bramleys from populated arraylist - 4
        @Test
        fun `list all bramleys from populated arraylist`(){
            assertEquals(7, addOutput!!.numberOfPacked())
            val noteOutput = addOutput!!.listBramleyOutputVTC()
            assertFalse(noteOutput.contains("6packSV"))
            assertTrue(noteOutput.contains("4packSV"))
            assertFalse(noteOutput.contains("4eatingCT"))
            assertTrue(noteOutput.contains("4bramleyCT"))
            assertFalse(noteOutput.contains("Premium 50"))
            assertTrue(noteOutput.contains("Loose 13kg"))
            assertTrue(noteOutput.contains("Loose large"))
        //End list bramleys -- populated arraylist
        }

    //End of Nested ListOutput class
    }

    //Test for count method
    @Nested
    inner class Counting {

        //Test to count all finished output - 7
        @Test
        fun `number of output`(){
            assertEquals(7, addOutput!!.numberOfPacked())
            assertEquals(0, emptyOutput!!.numberOfPacked())
        //End count all
        }

    //End of Nested Counting class
    }

//End of Test class
}