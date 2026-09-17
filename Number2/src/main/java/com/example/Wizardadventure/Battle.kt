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

        while (wizard.hp > 0 && enemy.hp > 0) {

            showBattleStatus(enemy)

            println("a. Fire Attack")
            println("b. Water Attack")
            println("c. Grass Attack")
            println("d. Drink potion")
            println("e. Run")

            print("Choose: ")
            val choice = readln().lowercase()

            when (choice) {

                "a" -> {
                    attack(enemy, Element.FIRE)
                }

                "b" -> {
                    attack(enemy, Element.WATER)
                }

                "c" -> {
                    attack(enemy, Element.GRASS)
                }

                "d" -> {
                    drinkPotion()
                }

                "e" -> {
                    println()
                    println("You ran away from the battle!")
                    return
                }

                else -> {
                    println()
                    println("Invalid choice.")
                    println("Please choose a, b, c, d, or e.")
                }
            }
        }

        if (enemy.hp <= 0) {
            winBattle(enemy)
        }

        if (wizard.hp <= 0) {
            loseBattle()
        }
    }

    // Creates a random enemy
    private fun createEnemy(): Enemy {

        val types = listOf(
            "Fire",
            "Water",
            "Grass"
        )

        val randomType = types.random()

        return Enemy(
            randomType,
            30
        )
    }

    // Shows the current battle information
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

    // Handles the wizard's attack
    private fun attack(
        enemy: Enemy,
        playerElement: Element
    ) {

        // Check mana
        if (wizard.mana < 10) {

            println()
            println("You don't have enough mana!")

            return
        }

        // Every spell costs 10 mana
        wizard.mana -= 10

        // Normal wizard damage = 10
        // Strong wizard damage = 15
        var damage = 10

        if (wizard.isStrongWizard) {
            damage = 15
        }

        val enemyElement = getEnemyElement(enemy)

        // Check elemental advantage
        if (beats(playerElement, enemyElement)) {

            damage *= 2

            println()
            println("It's super effective!")
        }

        enemy.hp -= damage

        println()
        println("${getAttackName(playerElement)} Attack!")
        println("You dealt $damage damage.")

        // Make sure enemy HP doesn't become negative
        if (enemy.hp < 0) {
            enemy.hp = 0
        }

        // Enemy attacks if still alive
        if (enemy.hp > 0) {

            enemyAttack()
        }

        // Strong Wizard gets lifesteal after attacking
        applyLifesteal()
    }

    // Converts enemy type String into Element
    private fun getEnemyElement(enemy: Enemy): Element {

        return when (enemy.type) {

            "Fire" -> Element.FIRE

            "Water" -> Element.WATER

            "Grass" -> Element.GRASS

            else -> Element.FIRE
        }
    }

    // Checks which element beats which
    private fun beats(
        playerElement: Element,
        enemyElement: Element
    ): Boolean {

        return when (playerElement) {

            // Fire beats Grass
            Element.FIRE -> {
                enemyElement == Element.GRASS
            }

            // Water beats Fire
            Element.WATER -> {
                enemyElement == Element.FIRE
            }

            // Grass beats Water
            Element.GRASS -> {
                enemyElement == Element.WATER
            }
        }
    }

    // Gets a readable attack name
    private fun getAttackName(element: Element): String {

        return when (element) {

            Element.FIRE -> "Fire"

            Element.WATER -> "Water"

            Element.GRASS -> "Grass"
        }
    }

    // Enemy attacks the wizard
    private fun enemyAttack() {

        val damage = 10

        wizard.hp -= damage

        if (wizard.hp < 0) {
            wizard.hp = 0
        }

        println()
        println("${wizard.name} received $damage damage from the enemy!")
    }

    // Potion menu during battle
    private fun drinkPotion() {

        println()
        println("Which potion?")
        println("1. Mana Potion")
        println("2. Health Potion")
        println("3. Back")

        print("Choose: ")

        val choice = readln()

        when (choice) {

            "1" -> {
                drinkManaPotion()
            }

            "2" -> {
                drinkHealthPotion()
            }

            "3" -> {
                println("You didn't drink a potion.")
            }

            else -> {
                println("Invalid potion choice.")
            }
        }
    }

    // Mana potion
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

        // Don't allow mana to exceed maximum
        if (wizard.mana > wizard.maxMana) {
            wizard.mana = wizard.maxMana
        }

        println()
        println("You drank a Mana Potion.")
        println("Mana restored by 15.")
        println("Mana: ${wizard.mana}/${wizard.maxMana}")
        println("Mana Potions left: ${wizard.manaPotions}")
    }

    // Health potion
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

        // Don't allow HP to exceed maximum
        if (wizard.hp > wizard.maxHp) {
            wizard.hp = wizard.maxHp
        }

        println()
        println("You drank a Health Potion.")
        println("HP restored by 25.")
        println("HP: ${wizard.hp}/${wizard.maxHp}")
        println("Health Potions left: ${wizard.healthPotions}")
    }

    // Lifesteal for Strong Wizard
    private fun applyLifesteal() {

        if (!wizard.isStrongWizard) {
            return
        }

        if (wizard.lifesteal <= 0) {
            return
        }

        val oldHp = wizard.hp

        wizard.hp += wizard.lifesteal

        if (wizard.hp > wizard.maxHp) {
            wizard.hp = wizard.maxHp
        }

        val actualHealing = wizard.hp - oldHp

        if (actualHealing > 0) {

            println()
            println("Lifesteal restored $actualHealing HP.")
        }
    }

    // Called when the wizard wins
    private fun winBattle(enemy: Enemy) {

        println()
        println("================================")
        println("${enemy.name} has been defeated!")
        println("================================")

        wizard.kills++

        println("You now have ${wizard.kills} kill(s).")

        // Increase lifesteal for an already evolved wizard
        if (wizard.isStrongWizard) {

            wizard.lifesteal++

            println()
            println("Lifesteal increased to ${wizard.lifesteal}.")
        }

        checkEvolution()
    }

    // Called when the wizard dies
    private fun loseBattle() {

        println()
        println("================================")
        println("${wizard.name} has been defeated!")
        println("================================")
        println()
        println("ULANG!")
    }

    // Checks whether the wizard should evolve
    private fun checkEvolution() {

        if (
            wizard.kills >= wizard.killsNeeded &&
            !wizard.isStrongWizard
        ) {

            wizard.isStrongWizard = true

            // 1.5x HP
            wizard.maxHp = 75
            wizard.hp = 75

            // 1.5x Mana
            wizard.maxMana = 45
            wizard.mana = 45

            // Lifesteal starts at 1
            wizard.lifesteal = 1

            println()
            println("================================")
            println("     YOU BECAME A STRONG WIZARD!")
            println("================================")
            println()
            println("Maximum HP: ${wizard.maxHp}")
            println("Maximum Mana: ${wizard.maxMana}")
            println("Damage: 15")
            println("Lifesteal: ${wizard.lifesteal}")
            println()
        }
    }
}