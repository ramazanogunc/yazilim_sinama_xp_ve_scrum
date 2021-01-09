package com.ramo.xpandscrum.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "card_table",
    foreignKeys = [ForeignKey(
        entity = Project::class,
        parentColumns = arrayOf("_id"),
        childColumns = arrayOf("projectId"),
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
    ]
)
data class Card(
    var name: String,
    var description: String,
    var note: String,
    var date: Date,
    var predictedMinute: Int,
    var projectId: Int,
    var cardType: CardType = CardType.TODO,
    var realMinute: Int? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var cardId: Int = 0
}
