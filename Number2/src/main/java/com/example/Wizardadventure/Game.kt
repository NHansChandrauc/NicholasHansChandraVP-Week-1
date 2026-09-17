package com.example.Wizardadventure

class Game(
    private val wizard: Wizard
) {

    fun start(): Boolean {

        while (true) {

            println()
            println("What're you going to do?")
            println("1. View Stats")
            println("2. Enter battle")
            println("3. Exit")

            print("Choose: ")

            val choice = readln().lowercase()

            when (choice) {

                "1" -> {
                    viewStats()
                }

                "2" -> {
                    val battle = Battle(wizard)
                    battle.start()

                    // Check if wizard died
                    if (wizard.hp <= 0) {
                        return true
                    }
                }

                "3" -> {
                    println()
                    println("Goodbye, ${wizard.name}!")
                    return false
                }

                else -> {
                    println()
                    println("Invalid choice.")
                    println("Please enter 1, 2, or 3.")
                }
            }
        }
    }

    private fun viewStats() {

        while (true) {

            println()
            println("========== STATS ==========")

            println("HP: ${wizard.hp}/${wizard.maxHp}")

            println("Mana: ${wizard.mana}/${wizard.maxMana}")

            println(
                "Kills needed to evolve: " +
                        "${wizard.kills}/${wizard.killsNeeded}"
            )

            println("Mana Potions held: ${wizard.manaPotions}")

            println("Health Potions held: ${wizard.healthPotions}")

            if (wizard.isStrongWizard) {
                println("Lifesteal: ${wizard.lifesteal}")
            }

            println()

            println("a. Drink Mana Potion")
            println("b. Drink Health Potion")
            println("c. Rename self")
            println("d. Back")

            println("============================")

            print("Choose: ")

            val choice = readln().lowercase()

            when (choice) {

                "a" -> {
                    drinkManaPotion()
                }

                "b" -> {
                    drinkHealthPotion()
                }

                "c" -> {
                    renameWizard()
                }

                "d" -> {
                    return
                }

                else -> {
                    println()
                    println("Invalid choice.")
                    println("Please choose a, b, c, or d.")
                }
            }
        }
    }

    private fun drinkManaPotion() {

        if (wizard.manaPotions <= 0) {

            println()
            println("You don't have any Mana Potions!")

            return
        }

        if (wizard.mana >= wizard.maxMana) {

            println()
            println("Your Mana is already full!")

            return
        }

        wizard.manaPotions--

        wizard.mana += 15

        if (wizard.mana > wizard.maxMana) {
            wizard.mana = wizard.maxMana
        }

        println()
        println("You drank a Mana Potion.")
        println("Mana restored by 15.")
        println("Mana: ${wizard.mana}/${wizard.maxMana}")
        println("Mana Potions left: ${wizard.manaPotions}")
    }

    private fun drinkHealthPotion() {

        if (wizard.healthPotions <= 0) {

            println()
            println("You don't have any Health Potions!")

            return
        }

        if (wizard.hp >= wizard.maxHp) {

            println()
            println("Your HP is already full!")

            return
        }

        wizard.healthPotions--

        wizard.hp += 25

        if (wizard.hp > wizard.maxHp) {
            wizard.hp = wizard.maxHp
        }

        println()
        println("You drank a Health Potion.")
        println("HP restored by 25.")
        println("HP: ${wizard.hp}/${wizard.maxHp}")
        println("Health Potions left: ${wizard.healthPotions}")
    }

    private fun renameWizard() {

        println()
        print("Enter your new name: ")

        val newName = readln().trim()

        if (newName.isEmpty()) {

            println("Name cannot be empty.")

            return
        }

        wizard.name = newName

        println()
        println("Your name is now ${wizard.name}.")
    }
}