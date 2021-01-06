package com.ramo.xpandscrum.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ramo.xpandscrum.model.Card
import com.ramo.xpandscrum.model.CardType

@Dao
interface CardDao {
    @Query("SELECT * FROM card_table WHERE projectId=:projectId AND cardType=:cardType")
    fun getAllCardFromByProjectAndCardType(projectId: Int, cardType: CardType): LiveData<List<Card>>

    @Query("SELECT * FROM card_table WHERE cardId=:id")
    suspend fun get(id: Int): Card?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(card: Card)

    @Update
    suspend fun update(card: Card)

    @Query("DELETE FROM card_table WHERE cardId=:id")
    suspend fun delete(id: Int)
}
