package com.ramo.xpandscrum.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ramo.xpandscrum.database.repository.CardRepository
import com.ramo.xpandscrum.model.Card
import com.ramo.xpandscrum.model.CardType
import kotlinx.coroutines.launch

class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {

    fun getAllCardFromByProjectAndCardType(projectId: Int, cardType: CardType) =
        cardRepository.getAllCardFromByProjectAndCardType(projectId, cardType)

    fun insert(card: Card) = viewModelScope.launch { cardRepository.insert(card) }

    fun update(card: Card) = viewModelScope.launch { cardRepository.update(card) }

    fun delete(cardId: Int) = viewModelScope.launch { cardRepository.delete(cardId) }

    fun validateAndInsert(card: Card, block: (message: String) -> Unit){
        // if validate()
        // else
        insert(card)
    }
}

class CardViewModelFactory(private val cardRepository: CardRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardViewModel(cardRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
