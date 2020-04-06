package snooper.mars.rover

import snooper.mars.rover.RoverFileParserListener.*
import java.io.File

/**
 * Simplistic parser for Rover commands file. For the sake of brevity it hasn't
 * been designed as a lexical parser, but a simple line-by-line file reader.
 */
class RoverFileParser(
    private val file: File,
    private val listener: RoverFileParserListener
) {

    val validUpperRightHeader = Regex("([0-9]+) ([0-9]+)")
    val validRoverInitialPosition = Regex("([0-9]+) ([0-9]+) ([NSWE])")
    val validRoverCommands = Regex("[LRM]+")

    constructor(fileName: String, listener: RoverFileParserListener): this(File(fileName), listener)

    fun parse() {
        val lines = file.readLines().filterNot { it.isBlank() }.toMutableList()
        parseUpperRightPositionHeader(lines)

        while (lines.isNotEmpty()) {
            parseRoverInitialPosition(lines)
            parseRoverCommands(lines)
        }
    }

    internal fun parseUpperRightPositionHeader(lines: FileLines) {
        val line = lines.consumeNextLine(expectation = "valid upper-right header")
        
        val result = validUpperRightHeader.matchEntire(line)
            ?: throw InvalidFileFormat("Invalid upper-right position: '$line'")

        listener.on(UpperRightCoordinates(
            x = result.groupValues[1].toInt(),
            y = result.groupValues[2].toInt()
        ))
    }

    internal fun parseRoverInitialPosition(lines: FileLines) {
        val line = lines.consumeNextLine("valid rover initial position")

        val result = validRoverInitialPosition.matchEntire(line)
            ?: throw InvalidFileFormat("Invalid rover initial position: '$line'")

        listener.on(RoverInitialPosition(
            x = result.groupValues[1].toInt(),
            y = result.groupValues[2].toInt(),
            direction = result.groupValues[3][0]
        ))
    }

    internal fun parseRoverCommands(lines: FileLines) {
        val line = lines.consumeNextLine("valid rover commands")

        validRoverCommands.matchEntire(line)
            ?: throw InvalidFileFormat("Invalid rover commands: '$line'")

        listener.on(line)
    }

    private fun FileLines.consumeNextLine(expectation: String): String {
        if (isEmpty())
            throw PrematureEndOfFile("Expected: $expectation")
        return removeAt(0)
    }
}

/**
 * A listener for the [RoverFileParser] events.
 */
interface RoverFileParserListener {

    fun on(coordinates: UpperRightCoordinates)
    fun on(position: RoverInitialPosition)
    fun on(commands: RoverCommands)

    data class UpperRightCoordinates(val x: Int, val y: Int)

    data class RoverInitialPosition(val x: Int, val y: Int, val direction: Char)
}

typealias RoverCommands = String
typealias FileLines = MutableList<String>
