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
    val cardId: Int?,
    val userId: Int?,
    val description: String? = null,
    val cardType: CardType? = null,
    val date: Date? = null
){
    @PrimaryKey(autoGenerate = true)
    var cardStatusId: Int = 0
}
