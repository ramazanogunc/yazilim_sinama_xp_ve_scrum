package com.ramo.xpandscrum.model

import androidx.room.Embedded
import androidx.room.Relation

data class CardStatusAndUser(

    @Embedded
    val cardStatus: CardStatus,

    @Relation(parentColumn = "userId", entityColumn = "userId")
    val user: User
)

