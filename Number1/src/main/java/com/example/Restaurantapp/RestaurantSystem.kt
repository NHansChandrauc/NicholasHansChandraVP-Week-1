package com.example.Restaurantapp

class RestaurantSystem {

    private val menu = mutableListOf<MenuItem>()
    private val orders = mutableListOf<Order>()

    init {
        menu.add(
            MenuItem(
                name = "Nasi Goreng",
                description = "Fried rice with chicken",
                price = 25000.0
            )
        )

        menu.add(
            MenuItem(
                name = "Ayam Bakar",
                description = "Grilled chicken",
                price = 30000.0
            )
        )

        menu.add(
            MenuItem(
                name = "Mie Goreng",
                description = "Fried noodles",
                price = 20000.0
            )
        )
    }

    fun getMenu(): MutableList<MenuItem> {
        return menu
    }

    fun getOrders(): MutableList<Order> {
        return orders
    }

    fun addMenuItem(
        name: String,
        description: String,
        price: Double
    ): Boolean {

        if (name.isBlank()) {
            return false
        }

        if (description.isBlank()) {
            return false
        }

        if (price <= 0) {
            return false
        }

        val item = MenuItem(
            name = name,
            description = description,
            price = price
        )

        menu.add(item)

        return true
    }

    fun editMenuItem(
        index: Int,
        name: String,
        description: String,
        price: Double
    ): Boolean {

        if (index < 0 || index >= menu.size) {
            return false
        }

        if (name.isBlank()) {
            return false
        }

        if (description.isBlank()) {
            return false
        }

        if (price <= 0) {
            return false
        }

        menu[index].name = name
        menu[index].description = description
        menu[index].price = price

        return true
    }

    fun deleteMenuItem(index: Int): Boolean {

        if (index < 0 || index >= menu.size) {
            return false
        }

        menu.removeAt(index)

        return true
    }

    fun makeOrder(
        customerName: String,
        selectedItems: MutableList<OrderItem>
    ): Boolean {

        if (customerName.isBlank()) {
            return false
        }

        if (selectedItems.isEmpty()) {
            return false
        }

        val order = Order(
            customerName = customerName,
            items = selectedItems
        )

        orders.add(order)

        return true
    }

    fun getTotalOrders(): Int {
        return orders.size
    }

    fun getTotalMenuItems(): Int {
        return menu.size
    }
}