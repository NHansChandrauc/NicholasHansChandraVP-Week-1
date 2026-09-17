package com.example.Wizardadventure

fun main() {

    while (true) {

        println()
        println("================================")
        println("       WIZARD ADVENTURE!")
        println("================================")

        print("What's your name? ")

        val name = readln().trim()

        if (name.isEmpty()) {

            println()
            println("Name cannot be empty.")

            continue
        }

        val wizard = Wizard(name)

        println()
        println(
            "Good luck, ${wizard.name}! " +
                    "You're gonna need it!"
        )

        val game = Game(wizard)

        val restart = game.start()

        if (!restart) {
            break
        }

        println()
        println("================================")
        println("             ULANG!")
        println("================================")
        println("Starting a new adventure...")
    }

    println()
    println("Thank you for playing!")
}