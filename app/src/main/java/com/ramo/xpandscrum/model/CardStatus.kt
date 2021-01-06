package com.ramo.xpandscrum.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "card_status_table")
data class CardStatus(
    val cardId: Int,
    val cardType: CardType? = null,
    val description: String? = null,
    val date: Date? = null,
    //val user: User
){
    @PrimaryKey(autoGenerate = true)
    var cardStatusId: Int = 0
}
