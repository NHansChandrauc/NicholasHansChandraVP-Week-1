package com.example.Restaurantapp

fun main() {

    val restaurant = RestaurantSystem()

    var running = true

    while (running) {

        println()
        println("================================")
        println("         ORDER SYSTEM")
        println("================================")
        println("1. Make order")
        println("2. View Orders")
        println("3. View Menu")
        println("4. Add Menu")
        println("5. Edit Menu")
        println("6. Delete Menu")
        println("7. Exit")
        println("================================")

        print("Choose an option: ")

        val choice = readlnOrNull()?.trim()

        when (choice) {

            "1" -> makeOrder(restaurant)

            "2" -> viewOrders(restaurant)

            "3" -> viewMenu(restaurant)

            "4" -> addMenu(restaurant)

            "5" -> editMenu(restaurant)

            "6" -> deleteMenu(restaurant)

            "7" -> {
                println()
                println("Thank you for using the Order System!")
                println("Goodbye!")

                running = false
            }

            else -> {
                println()
                println("ERROR: Invalid option.")
                println("Please choose a number from 1 to 7.")
                println()
                println("Returning to main menu...")
            }
        }
    }
}
fun viewMenu(restaurant: RestaurantSystem) {

    println()
    println("================================")
    println("            MENU")
    println("================================")

    val menu = restaurant.getMenu()

    if (menu.isEmpty()) {
        println("The menu is empty.")
        return
    }

    for (i in menu.indices) {

        val food = menu[i]

        println("${i + 1}. ${food.name}")
        println("   ${food.description}")
        println("   Price: Rp. ${"%.2f".format(food.price)}")
        println()
    }
}

fun addMenu(restaurant: RestaurantSystem) {

    println()
    println("================================")
    println("          ADD MENU")
    println("================================")

    val name = readRequiredText("Food name: ")

    val description = readRequiredText("Description: ")

    val price = readPositiveDouble("Price: Rp.  ")

    val success = restaurant.addMenuItem(
        name = name,
        description = description,
        price = price
    )

    if (success) {

        println()
        println("Menu item added successfully!")

    } else {

        println()
        println("ERROR: Could not add menu item.")
    }
}

fun editMenu(restaurant: RestaurantSystem) {

    println()
    println("================================")
    println("          EDIT MENU")
    println("================================")

    val menu = restaurant.getMenu()

    if (menu.isEmpty()) {
        println("ERROR: The menu is empty.")
        return
    }

    viewMenu(restaurant)

    val number = readPositiveInt(
        "Enter the menu number to edit: "
    )

    val index = number - 1

    if (index !in menu.indices) {

        println("ERROR: Menu number does not exist.")
        return
    }

    println()
    println("Enter the new information.")

    val name = readRequiredText("Food name: ")

    val description = readRequiredText("Description: ")

    val price = readPositiveDouble("Price: Rp. ")

    val success = restaurant.editMenuItem(
        index = index,
        name = name,
        description = description,
        price = price
    )

    if (success) {

        println()
        println("Menu item updated successfully!")

    } else {

        println()
        println("ERROR: Could not update menu item.")
    }
}

fun deleteMenu(restaurant: RestaurantSystem) {

    println()
    println("================================")
    println("         DELETE MENU")
    println("================================")

    val menu = restaurant.getMenu()

    if (menu.isEmpty()) {

        println("ERROR: The menu is empty.")
        return
    }

    viewMenu(restaurant)

    val number = readPositiveInt(
        "Enter the menu number to delete: "
    )

    val index = number - 1

    if (index !in menu.indices) {

        println("ERROR: Menu number does not exist.")
        return
    }

    val deletedFood = menu[index].name

    print(
        "Are you sure you want to delete " +
                "'$deletedFood'? (y/n): "
    )

    val confirmation =
        readlnOrNull()?.trim()?.lowercase()

    when (confirmation) {

        "y", "yes" -> {

            val success =
                restaurant.deleteMenuItem(index)

            if (success) {

                println()
                println(
                    "'$deletedFood' has been " +
                            "deleted successfully!"
                )

            } else {

                println()
                println(
                    "ERROR: Could not delete " +
                            "the menu item."
                )
            }
        }

        "n", "no" -> {

            println("Delete cancelled.")
        }

        else -> {

            println(
                "ERROR: Please enter y or n."
            )
        }
    }
}

fun makeOrder(restaurant: RestaurantSystem) {

    println()
    println("================================")
    println("          MAKE ORDER")
    println("================================")

    val menu = restaurant.getMenu()

    if (menu.isEmpty()) {

        println(
            "ERROR: There are no menu " +
                    "items available."
        )

        return
    }

    val customerName =
        readRequiredText("Customer name: ")

    val selectedItems =
        mutableListOf<OrderItem>()

    while (true) {

        println()

        viewMenu(restaurant)

        println(
            "Enter 0 when you have " +
                    "finished selecting items."
        )

        print("Choose menu number: ")

        val numberInput = readlnOrNull()

        val number =
            numberInput?.toIntOrNull()

        if (number == null) {

            println(
                "ERROR: Please enter " +
                        "a whole number."
            )

            continue
        }

        if (number == 0) {

            break
        }

        if (number < 0) {

            println(
                "ERROR: Menu number " +
                        "cannot be negative."
            )

            continue
        }

        val index = number - 1

        if (index !in menu.indices) {

            println(
                "ERROR: That menu number " +
                        "does not exist."
            )

            continue
        }

        val selectedFood = menu[index]

        val quantity = readPositiveInt(
            "Quantity for ${selectedFood.name}: "
        )

        val existingItem =
            selectedItems.find {
                it.food == selectedFood
            }

        if (existingItem != null) {

            existingItem.quantity += quantity

        } else {

            selectedItems.add(
                OrderItem(
                    food = selectedFood,
                    quantity = quantity
                )
            )
        }

        println(
            "${selectedFood.name} added " +
                    "to the order."
        )
    }

    if (selectedItems.isEmpty()) {

        println()
        println("ERROR: No items were selected.")
        println("The order was cancelled.")

        return
    }

    val success = restaurant.makeOrder(
        customerName = customerName,
        selectedItems = selectedItems
    )

    if (success) {

        val order = Order(
            customerName = customerName,
            items = selectedItems
        )

        println()
        println("================================")
        println("       ORDER CREATED")
        println("================================")
        println("${order.customerName}'s ORDER")
        println()

        for (item in order.items) {

            val subtotal =
                item.food.price * item.quantity

            println(
                "${item.food.name} x ${item.quantity} " +
                        "Price: Rp.  ${"%.2f".format(subtotal)}"
            )
        }

        println()
        println("--------------------------------")
        println(
            "TOTAL: Rp.  ${"%.2f".format(order.getTotal())}"
        )
        println("--------------------------------")

    } else {

        println()
        println("ERROR: Could not create the order.")
    }
}

fun viewOrders(restaurant: RestaurantSystem) {

    println()
    println("================================")
    println("          VIEW ORDERS")
    println("================================")

    val orders = restaurant.getOrders()

    if (orders.isEmpty()) {

        println("There are no orders yet.")

        return
    }

    for (i in orders.indices) {

        val order = orders[i]

        println()
        println("${order.customerName}'s ORDER")
        println("--------------------------------")

        for (item in order.items) {

            val subtotal =
                item.food.price * item.quantity

            println(
                "${item.food.name} x ${item.quantity} " +
                        "Price: Rp.  ${"%.2f".format(subtotal)}"
            )
        }

        println("--------------------------------")

        println(
            "TOTAL: Rp.  ${"%.2f".format(order.getTotal())}"
        )

        println("--------------------------------")
    }
}

fun readRequiredText(prompt: String): String {

    while (true) {

        print(prompt)

        val input =
            readlnOrNull()?.trim()

        if (!input.isNullOrEmpty()) {

            return input
        }

        println(
            "ERROR: This field cannot be empty."
        )

        println(
            "Please try again."
        )
    }
}

fun readPositiveInt(prompt: String): Int {

    while (true) {

        print(prompt)

        val input = readlnOrNull()

        val number =
            input?.toIntOrNull()

        if (number == null) {

            println(
                "ERROR: Please enter " +
                        "a whole number."
            )

            continue
        }

        if (number <= 0) {

            println(
                "ERROR: Number must be " +
                        "greater than 0."
            )

            continue
        }

        return number
    }
}

fun readPositiveDouble(prompt: String): Double {

    while (true) {

        print(prompt)

        val input = readlnOrNull()

        val number =
            input?.toDoubleOrNull()

        if (number == null) {

            println(
                "ERROR: Please enter " +
                        "a valid number."
            )

            continue
        }

        if (number <= 0) {

            println(
                "ERROR: Price must be " +
                        "greater than 0."
            )

            continue
        }

        return number
    }
}