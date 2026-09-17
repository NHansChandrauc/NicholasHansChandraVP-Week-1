package com.example.Wizardadventure

class Battle(
    private val wizard: Wizard
) {

    fun start() {
        val enemy = createEnemy()

        println()
        println("========== BATTLE ==========")
        println("${wizard.name}")
        println("HP: ${wizard.hp}/${wizard.maxHp}")
        println("Mana: ${wizard.mana}/${wizard.maxMana}")
        println("HP Potions: ${wizard.healthPotions}")
        println("MP Potions: ${wizard.manaPotions}")
        println()
        println("${enemy.name}")
        println("HP: ${enemy.hp}/${enemy.maxHp}")
        println("Type: ${enemy.type}")
        println("============================")

        while (enemy.hp > 0 && wizard.hp > 0) {
            showBattleStatus(enemy)

            println("a. Fire Attack")
            println("b. Water Attack")
            println("c. Grass Attack")
            println("d. Drink potion")
            println("e. Run")

            print("Choose: ")
            val choice = readln().lowercase()

            when (choice) {
                "a" -> attack(enemy, Element.FIRE)
                "b" -> attack(enemy, Element.WATER)
                "c" -> attack(enemy, Element.GRASS)
                "d" -> drinkPotion()
                "e" -> {
                    println("You ran away from the battle!")
                    return
                }
                else -> {
                    println("Invalid choice. Please choose a, b, c, d, or e.")
                }
            }
        }

        if (enemy.hp <= 0) {
            println()
            println("${enemy.name} has been defeated!")

            wizard.kills++

            println("You have ${wizard.kills} kill(s).")

            checkEvolution()
        }

        if (wizard.hp <= 0) {
            println()
            println("${wizard.name} has been defeated!")
            println("ULANG!")
        }
    }

    private fun createEnemy(): Enemy {
        val types = listOf("Fire", "Water", "Grass")
        val type = types.random()

        return Enemy(type, 30)
    }

    private fun showBattleStatus(enemy: Enemy) {
        println()
        println("---------- BATTLE ----------")
        println("${wizard.name}")
        println("HP: ${wizard.hp}/${wizard.maxHp}")
        println("Mana: ${wizard.mana}/${wizard.maxMana}")
        println("HP Potions: ${wizard.healthPotions}")
        println("MP Potions: ${wizard.manaPotions}")
        println()
        println("${enemy.name}")
        println("HP: ${enemy.hp}/${enemy.maxHp}")
        println("Type: ${enemy.type}")
        println("----------------------------")
    }

    private fun attack(enemy: Enemy, playerElement: Element) {
        if (wizard.mana < 10) {
            println("You don't have enough mana!")
            return
        }

        wizard.mana -= 10

        var damage = 10

        val enemyElement = when (enemy.type) {
            "Fire" -> Element.FIRE
            "Water" -> Element.WATER
            else -> Element.GRASS
        }

        if (beats(playerElement, enemyElement)) {
            damage *= 2
            println("It's super effective!")
        }

        enemy.hp -= damage

        println()
        println("${playerElement.name.lowercase().replaceFirstChar { it.uppercase() }} Attack!")
        println("You dealt $damage damage.")

        if (enemy.hp > 0) {
            enemyAttack()
        }
    }

    private fun beats(
        playerElement: Element,
        enemyElement: Element
    ): Boolean {
        return when (playerElement) {
            Element.FIRE -> enemyElement == Element.GRASS
            Element.WATER -> enemyElement == Element.FIRE
            Element.GRASS -> enemyElement == Element.WATER
        }
    }

    private fun enemyAttack() {
        wizard.hp -= 10

        println("${wizard.name} received 10 damage from the enemy!")
    }

    private fun drinkPotion() {
        println()
        println("Which potion?")
        println("1. Mana Potion")
        println("2. Health Potion")

        print("Choose: ")
        val choice = readln()

        when (choice) {
            "1" -> drinkManaPotion()
            "2" -> drinkHealthPotion()
            else -> println("Invalid potion choice.")
        }
    }

    private fun drinkManaPotion() {
        if (wizard.manaPotions <= 0) {
            println("You don't have any Mana Potions!")
            return
        }

        wizard.manaPotions--

        wizard.mana += 15

        if (wizard.mana > wizard.maxMana) {
            wizard.mana = wizard.maxMana
        }

        println("You restored 15 Mana.")
    }

    private fun drinkHealthPotion() {
        if (wizard.healthPotions <= 0) {
            println("You don't have any Health Potions!")
            return
        }

        wizard.healthPotions--

        wizard.hp += 25

        if (wizard.hp > wizard.maxHp) {
            wizard.hp = wizard.maxHp
        }

        println("You restored 25 HP.")
    }

    private fun checkEvolution() {
        if (wizard.kills >= wizard.killsNeeded && !wizard.isStrongWizard) {
            wizard.isStrongWizard = true

            wizard.maxHp = 75
            wizard.hp = 75

            wizard.maxMana = 45
            wizard.mana = 45

            wizard.lifesteal = 1

            println()
            println("================================")
            println("     YOU BECAME A STRONG WIZARD!")
            println("================================")
            println("HP increased to 75.")
            println("Mana increased to 45.")
            println("Lifesteal is now 1.")
        }
    }
}