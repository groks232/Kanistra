package com.groks.kanistra.feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hint(
    val hint: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
