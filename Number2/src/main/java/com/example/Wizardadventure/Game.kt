package com.example.Wizardadventure

class Game(
    private val wizard: Wizard
) {

    fun start() {
        while (true) {
            println()
            println("What're you going to do?")
            println("1. View Stats")
            println("2. Enter battle")
            println("3. Exit")

            print("Choose: ")
            val choice = readln()

            when (choice) {
                "1" -> viewStats()
                "2" -> println("Battle system coming next!")
                "3" -> {
                    println("Goodbye, ${wizard.name}!")
                    break
                }
                else -> {
                    println("Invalid choice. Please enter 1, 2, or 3.")
                }
            }
        }
    }

    private fun viewStats() {
        println()
        println("========== STATS ==========")
        println("HP: ${wizard.hp}/${wizard.maxHp}")
        println("Mana: ${wizard.mana}/${wizard.maxMana}")
        println("Kills needed to evolve: ${wizard.kills}/${wizard.killsNeeded}")
        println("Mana Potions held: ${wizard.manaPotions}")
        println("Health Potions held: ${wizard.healthPotions}")
        println("============================")
    }
}