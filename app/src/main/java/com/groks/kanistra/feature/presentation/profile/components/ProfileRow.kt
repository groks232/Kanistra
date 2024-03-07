package com.groks.kanistra.feature.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileRow(fullName: String) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                Icons.Filled.AccountCircle,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.3f)
            )
            Text(
                text = fullName,
                modifier = Modifier
                    .weight(0.5f)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}