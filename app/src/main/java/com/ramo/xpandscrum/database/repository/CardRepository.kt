package com.ramo.xpandscrum.database.repository

import com.ramo.xpandscrum.database.dao.CardDao
import com.ramo.xpandscrum.model.Card
import com.ramo.xpandscrum.model.CardType

class CardRepository(private val cardDao: CardDao) {

    fun getAllCardFromByProjectAndCardType(cardId: Int, cardType: CardType) =
        cardDao.getAllCardFromByProjectAndCardType(cardId, cardType)

    suspend fun get(cardId: Int) = cardDao.get(cardId)

    suspend fun insert(card: Card) = cardDao.insert(card)

    suspend fun update(card: Card) = cardDao.update(card)

    suspend fun delete(cardId: Int) = cardDao.delete(cardId)

}