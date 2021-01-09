package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.repository.ProjectRepository
import com.ramo.xpandscrum.databinding.FragmentAddEditProjectBinding
import com.ramo.xpandscrum.model.Project
import com.ramo.xpandscrum.showToast
import com.ramo.xpandscrum.viewModel.MainViewModel
import com.ramo.xpandscrum.viewModel.MainViewModelFactory

class AddProjectFragment : Fragment() {

    private val projectDao by lazy {
        AppDatabase.getInstance(requireContext()).projectDao
    }

    private val projectRepository by lazy {
        ProjectRepository(projectDao)
    }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(projectRepository)
    }


    private var addBinding: FragmentAddEditProjectBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addBinding = FragmentAddEditProjectBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return addBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addBinding?.let { it.btnSave.setOnClickListener { onSaveClick() } }

    }

    private fun onSaveClick() {
        validateInputAndRun {
            val project = getProjectFromUi()
            mainViewModel.insert(project)
            requireContext().showToast("Kaydedildi")
            requireActivity().onBackPressed()
        }
    }

    private fun validateInputAndRun(block: () -> Unit) {
        addBinding?.let {
            if (it.projectName.text.isNullOrEmpty())
                requireContext().showToast("Proje ismi boş geçemez")
            else if (it.projectName.text?.length!! > 50)
                requireContext().showToast("Proje 200 karakterden uzun olamaz")
            else
                block()
        }


    }

    private fun getProjectFromUi(): Project {
        return Project(
            addBinding!!.projectName.text.toString()
        )
    }
}