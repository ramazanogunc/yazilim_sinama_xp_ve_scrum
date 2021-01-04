package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.adapter.ProjectListAdapter
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.repository.ProjectRepository
import com.ramo.xpandscrum.databinding.FragmentMainBinding
import com.ramo.xpandscrum.model.Project
import com.ramo.xpandscrum.viewModel.MainViewModel
import com.ramo.xpandscrum.viewModel.MainViewModelFactory

class MainFragment : Fragment() {

    private val projectDao by lazy {
        AppDatabase.getInstance(requireContext()).projectDao
    }

    private val projectRepository by lazy {
        ProjectRepository(projectDao)
    }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(projectRepository)
    }

    private var mainBinding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainBinding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mainBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProjectListAdapter(::onItemClick,::onEditClick, ::onDeleteClick)
        with(mainBinding) {
            this!!.fabNewProject.setOnClickListener { onClickAddProject() }
            recyclerViewProjects.adapter = adapter
            recyclerViewProjects.layoutManager = LinearLayoutManager(requireContext())
        }

        mainViewModel.allProjects.observe(viewLifecycleOwner) { projectList ->
            projectList.let { adapter.submitList(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainBinding = null
    }

    private fun onClickAddProject() {
        findNavController().navigate(R.id.action_mainFragment_to_addProjectFragment)
    }

    private fun onItemClick(project: Project) {
        val bundle = bundleOf("id" to project._id)
        bundle.putString("name", project.name)
        findNavController().navigate(R.id.action_mainFragment_to_boardMasterFragment, bundle)
    }

    private fun onEditClick(project: Project) {
        val bundle = bundleOf("projectId" to project._id)
        bundle.putString("name", project.name)
        findNavController().navigate(R.id.action_mainFragment_to_editProjectFragment, bundle)
    }

    private fun onDeleteClick(project: Project) {
        mainViewModel.delete(project._id)
    }
}