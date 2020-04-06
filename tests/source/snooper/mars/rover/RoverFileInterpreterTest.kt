package snooper.mars.rover

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import snooper.mars.rover.RoverFileParserListener.*

@DisplayName("Behavior: Rover File Interpreter")
class RoverFileInterpreterTest {

    val upperRightCoordinates = UpperRightCoordinates(4, 5)
    val roverInitialPosition = RoverInitialPosition(1, 2, 'N')
    val roverFoundCommands = "LLM"

    val interpreter = RoverFileInterpreter()

    @DisplayName("SHOULD memorize upper-right coordinates for further usage")
    @Test fun onUpperRightCoordinates(){
        interpreter.on(upperRightCoordinates)
        
        val expectedMemorizedCoordinates = Coordinate(4, 5)
        assertEquals(expectedMemorizedCoordinates, interpreter.upperRightCoordinates)
    }
    
    @DisplayName("SHOULD memorize found rover and its initial position")
    @Test fun onRoverInitialPosition(){
        interpreter.on(upperRightCoordinates)
        interpreter.on(roverInitialPosition)

        val expectedMemorizedCoordinates = "1 2 N"
        assertEquals(expectedMemorizedCoordinates, interpreter.current.toString())
    }

    @DisplayName("SHOULD include the just found rover in the list of memorized rovers")
    @Test fun onRoverInitialPosition2(){
        interpreter.on(upperRightCoordinates)
        interpreter.on(roverInitialPosition)

        assertTrue(interpreter.current in interpreter.rovers)
    }

    @DisplayName("SHOULD perform found rover commands")
    @Test fun onRoverCommands(){
        interpreter.on(upperRightCoordinates)
        interpreter.on(roverInitialPosition)
        interpreter.on(roverFoundCommands)

        val expectedMemorizedCoordinates = "1 1 S"
        assertEquals(expectedMemorizedCoordinates, interpreter.current.toString())
    }

    @DisplayName("SHOULD read and move rovers WHEN interpreting valid file")
    @Test fun interpret(){
        val result = RoverFileInterpreter.interpret("tests/resources/valid-sample.mr")

        assertEquals(2, result.size)
        assertEquals("1 3 N", result[0])
        assertEquals("5 1 E", result[1])
    }
}