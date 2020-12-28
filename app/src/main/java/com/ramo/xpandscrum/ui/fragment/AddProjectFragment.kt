package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.model.Project
import com.ramo.xpandscrum.repository.ProjectRepository
import com.ramo.xpandscrum.showToast
import com.ramo.xpandscrum.viewModel.MainViewModel
import com.ramo.xpandscrum.viewModel.MainViewModelFactory

class AddProjectFragment : Fragment(R.layout.fragment_add_edit_project) {

    private lateinit var twProjectName: TextView

    private val projectDao by lazy {
        AppDatabase.getInstance(requireContext()).projectDao
    }

    private val projectRepository by lazy {
        ProjectRepository(projectDao)
    }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(projectRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnSave).setOnClickListener { onSaveClick() }

        twProjectName = view.findViewById(R.id.projectName)
    }

    private fun onSaveClick() {
        validateInputAndRun{
            val project = getProjectFromUi()
            mainViewModel.insert(project)
            requireContext().showToast("Kaydedildi")
            requireActivity().onBackPressed()
        }
    }

    private fun validateInputAndRun(block : () -> Unit){
        if (!twProjectName.text.isNullOrEmpty())
            block()
        else if (twProjectName.text.length > 200)
            requireContext().showToast("Proje 200 karakterden uzun olamaz")
        else
            requireContext().showToast("Proje ismi boş geçemez")

    }

    private fun getProjectFromUi(): Project {
        return Project(
            twProjectName.text.toString()
        )
    }
}