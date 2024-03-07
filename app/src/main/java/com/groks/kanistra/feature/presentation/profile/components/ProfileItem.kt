package com.groks.kanistra.feature.presentation.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.groks.kanistra.feature.presentation.profile.noRippleClickable

@Composable
fun ProfileItem(
    mainContent: String,
    secondaryContent: String? = null,
    onRowClick: () -> Unit
) {
    Row(
        Modifier
            .padding(if (!secondaryContent.isNullOrBlank()) 10.dp else 15.dp)
            .noRippleClickable { onRowClick() }) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(text = mainContent, style = MaterialTheme.typography.titleSmall)
                if(!secondaryContent.isNullOrBlank()) Text(text = secondaryContent, style = MaterialTheme.typography.bodyMedium)
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null
            )
        }
    }
}