package snooper.mars.rover

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: java -jar application.jar <file.name>")
        exitProcess(1)
    }

    val fileName = args[0]
    RoverFileInterpreter.interpret(fileName).forEach {
        println(it)
    }
}