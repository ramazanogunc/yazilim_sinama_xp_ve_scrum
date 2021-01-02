package com.ramo.xpandscrum.model

import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = ["id"],
            childColumns = ["projectId"],
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
    var cardId: Int = 0
}
