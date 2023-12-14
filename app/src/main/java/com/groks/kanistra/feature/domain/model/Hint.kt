package com.groks.kanistra.feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hint(
    val token: String,
    @PrimaryKey val id: Int? = null
)
