package snooper.mars.rover

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("Unit: Directions")
class DirectionTest {
    
    @DisplayName("SHOULD create a direction from character 'N'")
    @Test fun toDirection(){
        val direction = 'N'.toDirection()
        assertEquals(Direction.NORTH, direction)
    }
    
    @DisplayName("SHOULD create a direction from character 'S'")
    @Test fun toDirection2(){
        val direction = 'S'.toDirection()
        assertEquals(Direction.SOUTH, direction)
    }
    
    @DisplayName("SHOULD create a direction from character 'E'")
    @Test fun toDirection3(){
        val direction = 'E'.toDirection()
        assertEquals(Direction.EAST, direction)
    }

    @DisplayName("SHOULD create a direction from character 'W'")
    @Test fun toDirection4(){
        val direction = 'W'.toDirection()
        assertEquals(Direction.WEST, direction)
    }

    @DisplayName("SHOULD fail to create a direction from other characters")
    @Test fun toDirection5(){
        val failure = assertThrows<IllegalArgumentException> { 'V'.toDirection() }
        assertEquals("Unknown direction: V", failure.message)
    }
}