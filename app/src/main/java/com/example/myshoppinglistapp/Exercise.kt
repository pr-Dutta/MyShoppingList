package com.example.myshoppinglistapp

import androidx.compose.ui.text.toUpperCase

// (15-01-2024) - Map, Copy and let/ nullable method
fun main() {
//    /* map takes a list of item and iterate through all individual
//    * item do the operation defined */
//    val numbers = listOf(1, 2, 3)
//    val doubled = numbers.map { it * 2 }    // Result = [2, 4, 6]
//    println(doubled)

    /* copy method takes the original one then it makes a copy of it, and
    * we can apply our own variation on it - here we are working with objects */

//    val blueRoseVase = Vase("Blue", "Rose")
//    val redRoseVase = blueRoseVase.copy(color = "Red")
//    println(blueRoseVase)
//    println(redRoseVase)

//    // nullable string                                          -- Have to ask chatGpt?
//    val name: String? = "Ella"
//
//    /* If name is null then don't execute below then code for null safety */
//    name?.let {
//        println(it.toUpperCase())
//    }


    // Have to learn find function - (18-01-2024)
    val numbers = listOf(1, 2, 3, 20, 5, 6, 7, 8, 9, 10)

    /* Returns the first element matching the given predicate,
    * or null if no such element was found. */
    val result = numbers.find { it > 5 }

    if (result != null) {
        println("First element greater than 5: $result")
    } else {
        println("No element greater than 5 found.")
    }

}

data class Vase(val color: String, val design: String)






