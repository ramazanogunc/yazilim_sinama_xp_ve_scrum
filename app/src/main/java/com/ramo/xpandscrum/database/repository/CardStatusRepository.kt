package com.ramo.xpandscrum.database.repository

import com.ramo.xpandscrum.database.dao.CardStatusDao
import com.ramo.xpandscrum.model.CardStatus

class CardStatusRepository(private val cardStatusDao: CardStatusDao) {

    fun getAllCardStatusCardId(cardId: Int) = cardStatusDao.getAllCardStatusCardId(cardId)

    fun get(cardStatusId: Int) = cardStatusDao.get(cardStatusId)

    suspend fun insert(cardStatus: CardStatus) = cardStatusDao.insert(cardStatus)

    suspend fun update(cardStatus: CardStatus) = cardStatusDao.update(cardStatus)

    suspend fun delete(cardId: Int) = cardStatusDao.delete(cardId)

}