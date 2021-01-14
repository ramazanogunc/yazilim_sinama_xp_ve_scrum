package com.ramo.xpandscrum.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ramo.xpandscrum.database.repository.CardStatusRepository
import com.ramo.xpandscrum.database.repository.UserRepository
import com.ramo.xpandscrum.model.CardStatus
import kotlinx.coroutines.launch

/*
iş tabiki ekran modeli denilebilir.
repositorydeki methodlar kullanarak back end işlemlerini yapar.
 */
class CardStatusViewModel(
    private val cardStatusRepository: CardStatusRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    /*
    tüm kullanıcıları getirir.
     */
    val allUsers = userRepository.allUsers

    /*
    İlgili karttaki tüm iş takiplerini getirir
    */
    fun getAllCardStatusCardId(cardId: Int) =
        cardStatusRepository.getAllCardStatusCardId(cardId)
    /*
    Bir tane iş takibini getirir
    */
    fun get(cardStatusId: Int) = cardStatusRepository.get(cardStatusId)

    /*
    iş takibini kaydeder.
    */
    fun insert(cardStatus: CardStatus) =
        viewModelScope.launch { cardStatusRepository.insert(cardStatus) }

    /*
    İş takibini günceller
    */
    fun update(cardStatus: CardStatus) =
        viewModelScope.launch { cardStatusRepository.update(cardStatus) }

    /*
    İş takibini siler.
    */
    fun delete(cardId: Int) = viewModelScope.launch { cardStatusRepository.delete(cardId) }

}


/*
İlgili viewmodel sınıfnı üreten fabrika sınıfıdır.
Bu sınıf android işletim sisteminden dolayı kullanımı mecburidir.
 */
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
