package com.groks.kanistra.feature.presentation.cart.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun CartItemDropDownMenu(
    onDeleteButtonClick: () -> Unit,
    onShareButtonClick: () -> Unit,
) {
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier){
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Поделиться") },
                onClick = onShareButtonClick
            )
            DropdownMenuItem(
                text = { Text("Удалить", color = Color.Red) },
                onClick = onDeleteButtonClick
            )
        }
    }
}