package com.example.Wizardadventure
class Enemy(
    val type: String,
    val maxHp: Int
) {
    var hp = maxHp

    val name = "${type}mon"
}