package snooper.mars.rover

import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import snooper.mars.rover.RoverFileParserListener.*

@DisplayName("Behavior: File Parser")
class RoverFileParserTest {

    val listener = mock<RoverFileParserListener>()
    val parser = RoverFileParser("tests/resources/valid-sample.mr", listener)

    @DisplayName("SHOULD notify listener WHEN has parsed a valid upper-right header")
    @Test fun parseUpperRightPositionHeader()
    {
        val lines = mutableListOf(PreDefinitions.validUpperRightHeader)
        parser.parseUpperRightPositionHeader(lines)

        verify(listener).on(eq(UpperRightCoordinates(5,5)))
    }

    @DisplayName("SHOULD fail WHEN has parsed an invalid upper-right header")
    @Test fun parseUpperRightPositionHeader2()
    {
        val failure = assertThrows<InvalidFileFormat> {
            val lines = mutableListOf(PreDefinitions.invalidUpperRightHeader)
            parser.parseUpperRightPositionHeader(lines)
        }

        val expectedFailureMessage = "Invalid upper-right position: '${PreDefinitions.invalidUpperRightHeader}'"
        assertEquals(expectedFailureMessage, failure.message)
    }

    @DisplayName("SHOULD notify listener WHEN has parsed a valid rover initial position")
    @Test fun parseRoverInitialPosition()
    {
        val lines = mutableListOf(PreDefinitions.validRoverInitialPosition)
        parser.parseRoverInitialPosition(lines)

        verify(listener).on(eq(RoverInitialPosition(1,2, 'N')))
    }

    @DisplayName("SHOULD fail WHEN has parsed an invalid rover initial position")
    @Test fun parseRoverInitialPosition2()
    {
        val failure = assertThrows<InvalidFileFormat> {
            val lines = mutableListOf(PreDefinitions.invalidRoverInitialPosition)
            parser.parseRoverInitialPosition(lines)
        }

        val expectedFailureMessage = "Invalid rover initial position: '${PreDefinitions.invalidRoverInitialPosition}'"
        assertEquals(expectedFailureMessage, failure.message)
    }

    @DisplayName("SHOULD notify listener WHEN has parsed a valid set of rover commands")
    @Test fun parseRoverCommands()
    {
        val lines = mutableListOf(PreDefinitions.validSetOfRoverCommands)
        parser.parseRoverCommands(lines)

        verify(listener).on(eq(PreDefinitions.validSetOfRoverCommands))
    }

    @DisplayName("SHOULD fail WHEN has parsed an invalid set of rover commands")
    @Test fun parseRoverCommands2()
    {
        val failure = assertThrows<InvalidFileFormat> {
            val lines = mutableListOf(PreDefinitions.invalidSetOfRoverCommands)
            parser.parseRoverCommands(lines)
        }

        val expectedFailureMessage = "Invalid rover commands: '${PreDefinitions.invalidSetOfRoverCommands}'"
        assertEquals(expectedFailureMessage, failure.message)
    }

    @Nested
    @DisplayName("WHEN parsing valid file")
    inner class ParsingValidFile {

        @DisplayName("SHOULD notify listener Upper-Right Coordinates was found")
        @Test fun parse()
        {
            parser.parse()
    
            verify(listener).on(eq(UpperRightCoordinates(5,5)))
        }
    
        @DisplayName("SHOULD notify listener Rover Initial Position was found")
        @Test fun parse2()
        {
            parser.parse()
    
            verify(listener).on(eq(RoverInitialPosition(1,2, 'N')))
        }
    
        @DisplayName("SHOULD notify listener Rover Commands was found")
        @Test fun parse3()
        {
            parser.parse()
    
            verify(listener).on(eq(PreDefinitions.validSetOfRoverCommands))
        }
    }

    object PreDefinitions {

        val validUpperRightHeader = "5 5"
        val invalidUpperRightHeader = "5 A"

        val validRoverInitialPosition = "1 2 N"
        val invalidRoverInitialPosition = "1 2 9"

        val validSetOfRoverCommands = "LMLMLMLMM"
        val invalidSetOfRoverCommands = "9LMLMLMLMM"
    }
}