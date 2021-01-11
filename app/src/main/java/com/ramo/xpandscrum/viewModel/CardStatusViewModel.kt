package com.ramo.xpandscrum.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ramo.xpandscrum.database.repository.CardStatusRepository
import com.ramo.xpandscrum.database.repository.UserRepository
import com.ramo.xpandscrum.model.CardStatus
import kotlinx.coroutines.launch

class CardStatusViewModel(
    private val cardStatusRepository: CardStatusRepository,
    private val userRepository: UserRepository
) : ViewModel() {


    val allUsers = userRepository.allUsers

    fun getAllCardStatusCardId(cardId: Int) =
        cardStatusRepository.getAllCardStatusCardId(cardId)

    fun get(cardStatusId: Int) = cardStatusRepository.get(cardStatusId)

    fun insert(cardStatus: CardStatus) =
        viewModelScope.launch { cardStatusRepository.insert(cardStatus) }

    fun update(cardStatus: CardStatus) =
        viewModelScope.launch { cardStatusRepository.update(cardStatus) }

    fun delete(cardId: Int) = viewModelScope.launch { cardStatusRepository.delete(cardId) }

}

class CardStatusViewModelFactory(
    private val cardStatusRepository: CardStatusRepository,
    private val userRepository: UserRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CardStatusViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CardStatusViewModel(cardStatusRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
