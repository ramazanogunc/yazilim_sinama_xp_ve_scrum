package com.ramo.xpandscrum.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ramo.xpandscrum.database.repository.UserRepository
import com.ramo.xpandscrum.model.User
import kotlinx.coroutines.launch

/*
Kullanıcı ekran modeli denilebilir.
repositorydeki methodlar kullanarak back end işlemlerini yapar.
 */
class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    /*
    tüm kullanıcıları getirir
     */
    val allUsers = userRepository.allUsers

    /*
    kullanıcıları kaydeder
     */
    fun insert(user: User) = viewModelScope.launch { userRepository.insert(user) }

}

/*
İlgili viewmodel sınıfnı üreten fabrika sınıfıdır.
Bu sınıf android işletim sisteminden dolayı kullanımı mecburidir.
 */
class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}