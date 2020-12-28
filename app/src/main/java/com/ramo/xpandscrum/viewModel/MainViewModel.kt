package com.ramo.xpandscrum.viewModel

import androidx.lifecycle.*
import com.ramo.xpandscrum.model.Project
import com.ramo.xpandscrum.repository.ProjectRepository
import kotlinx.coroutines.launch

class MainViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    val allProjects : LiveData<List<Project>> = projectRepository.allProjects.asLiveData()

    fun insert(project: Project) = viewModelScope.launch {
        projectRepository.insert(project)
    }


}

class MainViewModelFactory(private val repository: ProjectRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
