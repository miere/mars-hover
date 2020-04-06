package snooper.mars.rover

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("Behavior: Rover Commands")
class RoverTest {

    val roverAtBottomLeftCornerHeadingSouth = Rover(
        position = Coordinate(0, 0),
        direction = Direction.SOUTH,
        upperRightCoordinate = Coordinate(5, 5)
    )

    val roverAtTopRightCornerHeadingNorth = Rover(
        position = Coordinate(5, 5),
        direction = Direction.NORTH,
        upperRightCoordinate = Coordinate(5, 5)
    )

    val roverInTheCenterOfMarsHeadingNorth = Rover(
        position = Coordinate(3, 3),
        direction = Direction.NORTH,
        upperRightCoordinate = Coordinate(5, 5)
    )

    @DisplayName("SHOULD be capable to turn itself to right")
    @Test fun turnRight(){
        val rover = roverAtTopRightCornerHeadingNorth

        rover.turnRight()
        assertEquals("5 5 E", rover.toString())
        rover.turnRight()
        assertEquals("5 5 S", rover.toString())
        rover.turnRight()
        assertEquals("5 5 W", rover.toString())
        rover.turnRight()
        assertEquals("5 5 N", rover.toString())
    }

    @DisplayName("SHOULD be capable to turn itself to left")
    @Test fun turnLeft(){
        val rover = roverAtTopRightCornerHeadingNorth

        rover.turnLeft()
        assertEquals("5 5 W", rover.toString())
        rover.turnLeft()
        assertEquals("5 5 S", rover.toString())
        rover.turnLeft()
        assertEquals("5 5 E", rover.toString())
        rover.turnLeft()
        assertEquals("5 5 N", rover.toString())
    }

    @DisplayName("SHOULD be capable to move in the previously defined direction")
    @Test fun move(){
        val rover = roverAtBottomLeftCornerHeadingSouth

        rover.turnLeft().move()
        assertEquals("1 0 E", rover.toString())
        rover.turnLeft().move()
        assertEquals("1 1 N", rover.toString())
        rover.turnLeft().move()
        assertEquals("0 1 W", rover.toString())
        rover.turnLeft().move()
        assertEquals("0 0 S", rover.toString())
    }

    @DisplayName("WHEN performing action from read char")
    @Nested inner class WhenPerformingActionFromReadChar {
        
        @DisplayName("SHOULD turn left when performing command 'L'")
        @Test fun perform(){
            val rover = roverAtBottomLeftCornerHeadingSouth
            rover.perform('L')
            assertEquals("0 0 E", rover.toString())
        }

        @DisplayName("SHOULD turn right when performing command 'R'")
        @Test fun perform2(){
            val rover = roverAtBottomLeftCornerHeadingSouth
            assertEquals("0 0 S", rover.toString())
            rover.perform('R')
            assertEquals("0 0 W", rover.toString())
        }

        @DisplayName("SHOULD turn right when performing command 'M'")
        @Test fun perform3(){
            val rover = roverInTheCenterOfMarsHeadingNorth
            rover.perform('M')
            assertEquals("3 4 N", rover.toString())
        }
    }
    
    @DisplayName("SHOULD never move out of bounds")
    @Nested inner class ShouldNeverMoveOutOfBounds {
        
        @DisplayName("WHEN asked to move NORTH")
        @Test fun move(){
            val rover = roverAtTopRightCornerHeadingNorth
            assertEquals("5 5 N", rover.toString())

            rover.move()
            assertEquals("5 5 N", rover.toString())
        }
        
        @DisplayName("WHEN asked to move EAST")
        @Test fun move2(){
            val rover = roverAtTopRightCornerHeadingNorth
            rover.turnRight()
            assertEquals("5 5 E", rover.toString())

            rover.move()
            assertEquals("5 5 E", rover.toString())
        }
        
        @DisplayName("WHEN asked to move SOUTH")
        @Test fun move3(){
            val rover = roverAtBottomLeftCornerHeadingSouth
            assertEquals("0 0 S", rover.toString())

            rover.move()
            assertEquals("0 0 S", rover.toString())
        }

        @DisplayName("WHEN asked to move WEST")
        @Test fun move4(){
            val rover = roverAtBottomLeftCornerHeadingSouth
            rover.turnRight()
            assertEquals("0 0 W", rover.toString())

            rover.move()
            assertEquals("0 0 W", rover.toString())
        }
    }
}