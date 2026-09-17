package com.example.Wizardadventure

class Wizard(
    var name: String
) {
    var maxHp = 50
    var hp = 50

    var maxMana = 30
    var mana = 30

    var kills = 0
    var killsNeeded = 5

    var manaPotions = 5
    var healthPotions = 5

    var isStrongWizard = false
    var lifesteal = 0
}