package snooper.mars.rover

/**
 * Represents the Rover.
 */
class Rover(
    private var position: Coordinate,
    private var direction: Direction,
    private val upperRightCoordinate: Coordinate
) {

    fun perform(command: Char) = when(command) {
        'L' -> turnLeft()
        'R' -> turnRight()
        'M' -> move()
        else -> throw IllegalArgumentException("Invalid command: $command")
    }

    internal fun turnLeft()  = turnBy(Direction.values().size - 1)
    internal fun turnRight() = turnBy(1)

    private fun turnBy(n: Int): Rover {
        val next = (direction.ordinal + n) % Direction.values().size
        direction = Direction.values()[next]
        return this
    }

    internal fun move(): Rover {
        position = Coordinate(
            x = (position.x + direction.moveOffsetX).between(0, upperRightCoordinate.x),
            y = (position.y + direction.moveOffsetY).between(0, upperRightCoordinate.y)
        )
        return this
    }

    private fun Int.between(min: Int, max: Int): Int {
        return maxOf(min, minOf(this, max))
    }

    override fun toString(): String {
        return "${position.x} ${position.y} ${direction.representation}"
    }
}

data class Coordinate(var x: Int, var y: Int)