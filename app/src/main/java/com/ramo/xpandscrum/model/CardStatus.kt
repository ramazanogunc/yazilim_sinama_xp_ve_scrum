package com.ramo.xpandscrum.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "card_status_table",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = Card::class,
            parentColumns = arrayOf("cardId"),
            childColumns = arrayOf("cardId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class CardStatus(
    val cardId: Int,
    val cardType: CardType? = null,
    val description: String? = null,
    val date: Date? = null,
    val userId: Int
){
    @PrimaryKey(autoGenerate = true)
    var cardStatusId: Int = 0
}
