package com.example.myshoppinglistapp.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// (04-01-2024) -> have to revise it. - Done
data class ShoppingItem(val id: Int,
                        var name: String,
                        var quantity: Int,
                        var isEditing: Boolean = false
)

@Composable
fun ShoppingListApp() {
    // (04-01-2024) -> have to revise it. - Done
    var sItems by remember{ mutableStateOf(listOf<ShoppingItem>()) }
    // If the showDialog is false then the Dialog should be hide else shown
    var showDialog by remember{ mutableStateOf(false) }
    var itemName by remember{ mutableStateOf("") }
    var itemQuantity by remember{ mutableStateOf("") }


    // (03-01-2024)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Item")        //Lazy Column - Revised - Done

            // 04-01-2024
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            /* The items function inside the LazyColumn is responsible
             * for dynamically loading and rendering only the items that
             * are currently visible on the screen*/
            items(sItems) {
                // How it represents shoppingItem?
                ShoppingListItem(it, {},{}) // (11-01-2024) What the hell is going on here just understood.
            }
        }
    }


    // (07-01-2024) - Have to revise
    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false },
            confirmButton = {                                       // (09-01-2024)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {  // onClick is an lambda function - (11-01-2024)
                        if (itemName.isNotBlank()) {
                            val newItem = ShoppingItem(
                                id = sItems.size+1,
                                name = itemName,
                                quantity = itemQuantity.toInt()
                            )
                            sItems = sItems + newItem
                            showDialog = false
                            itemName = ""
                        }
                    }) {
                        //val doubleNumber: (Int) -> Int = { it * 2 }     // lambda expression (12-01-2024)
                        //Text(text = doubleNumber(5).toString())

                        /* Don't do this type on calculation
                        * inside UI element, it may  freeze your UI */

                        Text(text = "Add")                    }
                    Button(onClick = {
                        showDialog = false
                    }) {
                        Text(text = "Cancel")
                    }
                }
            },
            title = { Text("Add Shopping Item") },
            // Here we miss use the text composable
            text = {
                Column {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                        )
                    OutlinedTextField(
                        value = itemQuantity,
                        onValueChange = { itemQuantity = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                        )
                    }
                }
            )
    }
}


// (11-01-2024)
@Composable
/* Here we can pass function and that function will be executed
* when the ShoppingListItem() fun will call */
fun ShoppingListItem(
    item: ShoppingItem,
    onEditClick: () -> Unit,        //This is a lambda function - have to learn ask chatGpt? - Done
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                // BorderStroke is a Composable and it creates Border around UI elements. (11-01-2024)
                border = BorderStroke(2.dp, Color(0XFF018786)),
                shape = RoundedCornerShape(20)
            )
    ) {

        //  (12-01-2024)

        Text(text = item.name, Modifier.padding(8.dp))  // 8.dp are preferred to position items
        Text(text = "Qty: ${item.quantity}", Modifier.padding(8.dp))
        Row(modifier = Modifier.padding(8.dp)) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}










