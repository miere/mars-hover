package snooper.mars.rover

import snooper.mars.rover.RoverFileParserListener.*

class RoverFileInterpreter: RoverFileParserListener {

    internal val rovers = mutableListOf<Rover>()
    internal lateinit var upperRightCoordinates: Coordinate
    internal lateinit var current: Rover

    override fun on(coordinates: UpperRightCoordinates) {
        this.upperRightCoordinates = Coordinate(
            x = coordinates.x,
            y = coordinates.y
        )
    }

    override fun on(position: RoverInitialPosition) {
        current = Rover(
            upperRightCoordinate = this.upperRightCoordinates,
            direction = position.direction.toDirection(),
            position = Coordinate(position.x, position.y)
        )
        rovers.add(current)
    }

    override fun on(commands: RoverCommands) {
        for (command in commands)
            current.perform(command)
    }

    companion object {

        fun interpret(fileName: String): List<String> {
            val listener = RoverFileInterpreter()
            RoverFileParser(fileName, listener).parse()
            return listener.rovers.map { it.toString() }
        }
    }
}