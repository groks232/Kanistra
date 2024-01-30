package com.groks.kanistra.feature.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.groks.kanistra.feature.domain.model.Hint

@Dao
interface HintDao {
    @Query("SELECT * FROM hint")
    suspend fun getHints(): List<Hint>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHint(hint: Hint)

    @Query("DELETE FROM hint where id = :id")
    suspend fun deleteHint(id: Int)
}