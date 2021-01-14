package com.ramo.xpandscrum.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ramo.xpandscrum.database.repository.ProjectRepository
import com.ramo.xpandscrum.model.Project
import kotlinx.coroutines.launch

/*
Ana ekranın modeli denilebilir.
repositorydeki methodlar kullanarak back end işlemlerini yapar.
 */
class MainViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    /*
    tüm projeleri getirir
     */
    val allProjects: LiveData<List<Project>> = projectRepository.allProjects

    /*
    projeyi kaydeder
     */
    fun insert(project: Project) = viewModelScope.launch {
        projectRepository.insert(project)
    }

    /*
    projeyi getirir
     */
    fun getProject(id: Int, block: (project: Project?) -> Unit) = viewModelScope.launch {
        block(projectRepository.get(id))
    }

    /*
    projeyi günceller
     */
    fun update(project: Project) = viewModelScope.launch { projectRepository.update(project) }

    /*
    projeyi siler
     */
    fun delete(projectId: Int) = viewModelScope.launch { projectRepository.delete(projectId) }


}

/*
İlgili viewmodel sınıfnı üreten fabrika sınıfıdır.
Bu sınıf android işletim sisteminden dolayı kullanımı mecburidir.
 */
class MainViewModelFactory(private val repository: ProjectRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
