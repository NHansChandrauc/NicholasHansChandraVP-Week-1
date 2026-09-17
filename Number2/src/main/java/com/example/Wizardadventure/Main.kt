package com.example.Wizardadventure

fun main() {
    println("================================")
    println("       WIZARD ADVENTURE!")
    println("================================")

    print("What's your name? ")
    val name = readln()

    val wizard = Wizard(name)

    println()
    println("Good luck, ${wizard.name}! You're gonna need it!")

    val game = Game(wizard)
    game.start()
}