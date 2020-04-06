package snooper.mars.rover

import java.lang.IllegalArgumentException

enum class Direction(
    val moveOffsetX: Int,
    val moveOffsetY: Int,
    val representation: Char
){
    NORTH(0, 1, 'N'),
    EAST(1, 0, 'E'),
    SOUTH(0, -1, 'S'),
    WEST(-1, 0, 'W');
}

fun Char.toDirection() = when(this) {
    'N' -> Direction.NORTH
    'S' -> Direction.SOUTH
    'E' -> Direction.EAST
    'W' -> Direction.WEST
    else -> throw IllegalArgumentException("Unknown direction: $this")
}
