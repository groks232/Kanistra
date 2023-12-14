package com.groks.kanistra.feature.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.groks.kanistra.feature.domain.model.Hint

@Database(
    entities = [Hint::class],
    version = 1
)
abstract class HintDatabase: RoomDatabase() {
    abstract val hintDao: HintDao

    companion object {
        const val DATABASE_NAME = "token_db"
    }
}