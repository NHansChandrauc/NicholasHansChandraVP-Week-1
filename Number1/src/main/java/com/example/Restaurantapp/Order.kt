package com.example.Restaurantapp

import kotlin.times

data class Order(
    val customerName: String,
    val items: MutableList<OrderItem>
) {

    fun getTotal(): Double {
        return items.sumOf {
            it.food.price * it.quantity
        }
    }
}