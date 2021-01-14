package com.ramo.xpandscrum.model

import androidx.room.Embedded
import androidx.room.Relation

/*
iş takibi be kullanıc içeren model
inner join işlemi gerçekleştirilmiş modeldir.
aynı zamanda veritabanı için de kullanılır
 */
data class CardStatusAndUser(

    @Embedded
    val cardStatus: CardStatus,

    @Relation(parentColumn = "userId", entityColumn = "userId")
    val user: User
)

