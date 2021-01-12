package com.ramo.xpandscrum.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ramo.xpandscrum.model.CardStatus
import com.ramo.xpandscrum.model.CardStatusAndUser

@Dao
interface CardStatusDao {
    @Transaction
    @Query("SELECT * FROM card_status_table WHERE cardId=:cardId")
    fun getAllCardStatusCardId(cardId: Int): LiveData<List<CardStatusAndUser>>

    @Query("SELECT * FROM card_status_table WHERE cardStatusId=:id")
    fun get(id: Int): LiveData<CardStatus>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cardStatus: CardStatus)

    @Update
    suspend fun update(cardStatus: CardStatus)

    @Query("DELETE FROM card_status_table WHERE cardStatusId=:id")
    suspend fun delete(id: Int)
}
