package snooper.mars.rover

sealed class MarsRoverException(message: String): RuntimeException(message)

class InvalidFileFormat(message: String): MarsRoverException(message)

class PrematureEndOfFile(message: String): MarsRoverException(message)
