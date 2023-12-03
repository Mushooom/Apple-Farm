package controllers

//Imports
import models.OutputPL
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

//Date
private var currentTime = LocalDateTime.now()!!
var time3 = currentTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))!!


class OutputPLAPITest {
    private var eating72: OutputPL? = null
    private var eating96: OutputPL? = null
    private var bramleyPL: OutputPL? = null
    private var bramleyJuice: OutputPL? = null
    private var addOutputPL: OutputPLAPI? = OutputPLAPI(JSONSerializer(File("outputPL.json")))
    private var emptyOutputPL: OutputPLAPI? = OutputPLAPI(JSONSerializer(File("outputPL.json")))
    

    @BeforeEach // Dummy Data -- set values and load into APP arraylist - 4
    fun setup(){
        eating72 = OutputPL("28", true, "Red elstar", "Count 72", 144, time3)
        eating96 = OutputPL("17", true, "Red elstar", "Count 96", 120, time3)
        bramleyPL = OutputPL("pl", false, "Bramley", "13kg", 30, time3)
        bramleyJuice = OutputPL("4", false, "Bramley", "Juice", 50, time3)

        // Adding the outputPL values to arraylist - 4
        addOutputPL!!.addOutputPL(eating72!!)
        addOutputPL!!.addOutputPL(eating96!!)
        addOutputPL!!.addOutputPL(bramleyPL!!)
        addOutputPL!!.addOutputPL(bramleyJuice!!)
    //End of setup
    }

    @AfterEach // Delete all values after each test -- clean the arraylist
    fun tearDown(){
        eating72 = null
        eating96 = null
        bramleyPL = null
        bramleyJuice = null
        addOutputPL = null
        emptyOutputPL = null
    //End of tearDown
    }

    //Test for adding the outputPL
    @Nested 
    inner class AddOutputPL {

        @Test //Test to add outputPL to empty arraylist
        fun `adding outputPL to empty arraylist`(){
            val newOutputPL = OutputPL("4", false, "Bramley", "Juice", 50, time3)
            assertEquals(0, emptyOutputPL!!.numberOfOutputPL())
            assertTrue(emptyOutputPL!!.addOutputPL(newOutputPL))
            assertEquals(1, emptyOutputPL!!.numberOfOutputPL())
        //End of add outputPL -- empty arraylist
        }

        @Test //Test to add outputPl to populated arraylist - 4
        fun `adding outputPL to populated arraylist`(){
            val newOutputPL = OutputPL("4", false, "Bramley", "Juice", 50, time3)
            assertEquals(4, addOutputPL!!.numberOfOutputPL())
            assertTrue(addOutputPL!!.addOutputPL(newOutputPL))
            assertEquals(5, addOutputPL!!.numberOfOutputPL())
        //End of adding to populated arraylist
        }

    //End of Nested AddOutput
    }

    //Test class for listing the outputPL
    @Nested
    inner class ListOutputPL {

        //Test to list all from empty arraylist
        @Test
        fun `list all outputPL form empty arraylist`(){
            assertEquals(0, emptyOutputPL!!.numberOfOutputPL())
            assertTrue(emptyOutputPL!!.listOutputPL().contains("Nothing packed for Phillip today"))
        //End list all -- empty arraylist
        }

        //Test to list all from populated arraylist - 4
        @Test
        fun `list all outputPL from populated arraylist`(){
            assertEquals(4, addOutputPL!!.numberOfOutputPL())
            val noteOutputPL = addOutputPL!!.listOutputPL()
            assertTrue(noteOutputPL.contains("Count 72"))
            assertTrue(noteOutputPL.contains("Count 96"))
            assertTrue(noteOutputPL.contains("13kg"))
            assertTrue(noteOutputPL.contains("Juice"))
        //End list all -- populated arraylist
        }

    //End of Nested ListOutputPL
    }

//End of Test class
}