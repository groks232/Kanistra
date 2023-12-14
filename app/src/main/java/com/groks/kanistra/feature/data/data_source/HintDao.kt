package com.groks.kanistra.feature.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.groks.kanistra.feature.domain.model.Hint

@Dao
interface HintDao {
    @Query("SELECT * FROM hint WHERE id = :id")
    suspend fun getTokenById(id: Int): Hint?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(hint: Hint)

    @Query("DELETE FROM hint")
    suspend fun deleteToken()
}