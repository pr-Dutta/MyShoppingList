package com.example.myshoppinglistapp.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// (04-01-2024) -> have to revise it. - Done
data class ShoppingItem(val id: Int,
                        var name: String,
                        var quantity: Int,
                        var isEditing: Boolean = false
)

@Composable                                         // I have to give more time to understand this
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
                //ShoppingListItem(it, {},{}) // (11-01-2024) What the hell is going on here just understood.


                // (16-01-2024)
                item ->
                if (item.isEditing) {
                    ShoppingItemEditor(item = item, onEditComplete = {
                        editedName, editedQuantity ->
                        /* it means individual sItems*/
                        sItems = sItems.map { it.copy(isEditing =  false) }
                        val editedItem = sItems.find { it.id == item.id }       // new function find
                        editedItem?.let {
                            it.name = editedName
                            it.quantity = editedQuantity
                        }
                    })
                }else {                                                         // - 18-01-2024
                    ShoppingListItem(item = item, onEditClick = {
                        // finding out which item we are edition and changing it's
                        // "isEditing boolean" to true
                        sItems = sItems.map { it.copy(isEditing = it.id == item.id) }
                    }, onDeleteClick = {
                        sItems = sItems - item
                    })
                }
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
                    /* SpaceBetween gives as mush as space, in between two UI element     - (13-01-2024) */
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

// (13-01-2024)
@Composable
fun ShoppingItemEditor(
    item: ShoppingItem,
   onEditComplete: (String, Int) -> Unit) {

    var editedName by remember { mutableStateOf(item.name) }
    var editedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(item.isEditing) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(8.dp),
        /* It will give space between Ui element evenly means equally            -- (13-01-2024) */
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {
            BasicTextField(
                value = editedName,
                onValueChange = { editedName = it },
                singleLine = true,
                /* wrapContentSize will take only the minimum required size */
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
            BasicTextField(
                value = editedQuantity,
                onValueChange = { editedQuantity = it },
                singleLine = true,
                /* wrapContentSize will take only the minimum required size */
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }
        
        Button(
            onClick = {
                isEditing = false // Why we call the onEditComplete function here inside the function itself
                onEditComplete(editedName, editedQuantity.toIntOrNull() ?: 1)      // Elvis operator here
            }
        ) {
            Text(text = "Save")
        }
        
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
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // (12-01-2024)

        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = item.name, Modifier.padding(8.dp))  // 8.dp are preferred to position items
            Text(text = "Qty: ${item.quantity}", Modifier.padding(8.dp))
        }

        Row(modifier = Modifier.padding(8.dp)) {
            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }

            /* Icon buttons help people take supplementary actions  // (13-01-2024) - Have to revise again
            * with a single tap. They’re used when a compact button
            * is required, such as in a toolbar or image list. */
            IconButton(onClick = onDeleteClick) {
                /* Icon is a Material Design icon component that draws imageVector  // (13-01-2024) - Have to revise again
                 * using tint, with a default value of LocalContentColor.
                 * If imageVector has no intrinsic size, this component will
                 * use the recommended default size. */
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}



@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "PracticeApp"
)
@Composable
fun MyShoppingListAppPreview() {
    MyShoppingListAppTheme {
        ShoppingListApp()
    }
}






