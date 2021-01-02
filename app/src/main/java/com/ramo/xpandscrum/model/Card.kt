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
        onDelete = ForeignKey.CASCADE
    )
    ]
)
data class Card(
    var date: Date,
    var technichPerson: String,
    var description: String,
    var note: String,
    // eklenecekler var

    // relionships
    var boardType: BoardType = BoardType.TODO,
    var projectId: Int = 0
) {
    @PrimaryKey(autoGenerate = true)
    var cardId: Int = 0
}
