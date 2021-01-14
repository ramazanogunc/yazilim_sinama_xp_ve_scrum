package com.ramo.xpandscrum.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ramo.xpandscrum.database.repository.CardRepository
import com.ramo.xpandscrum.model.Card
import com.ramo.xpandscrum.model.CardType
import kotlinx.coroutines.launch

/*
Kart ekran modeli denilebilir.
repositorydeki methodlar kullanarak back end işlemlerini yapar.
 */
class CardViewModel(private val cardRepository: CardRepository) : ViewModel() {

    /*
    İlgil kriterlere göre karları getirir
     */
    fun getAllCardFromByProjectAndCardType(projectId: Int, cardType: CardType) =
        cardRepository.getAllCardFromByProjectAndCardType(projectId, cardType)

    /*
    kart getirir.
    */
    fun getCard(cardId: Int) = cardRepository.getCard(cardId)

    /*
    kart getirir.
    */
    fun get(cardId: Int) = viewModelScope.launch { cardRepository.get(cardId) }

    /*
    kart kaydeder.
    */
    fun insert(card: Card) = viewModelScope.launch { cardRepository.insert(card) }

    /*
    kart günceller.
    */
    fun update(card: Card) = viewModelScope.launch { cardRepository.update(card) }

    /*
    kart siler.
    */
    fun delete(cardId: Int) = viewModelScope.launch { cardRepository.delete(cardId) }

    /*
    kart kaydeder.
    */
    fun validateAndInsert(card: Card, block: (message: String) -> Unit) {
        insert(card)
    }
}

/*
İlgili viewmodel sınıfnı üreten fabrika sınıfıdır.
Bu sınıf android işletim sisteminden dolayı kullanımı mecburidir.
 */
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
