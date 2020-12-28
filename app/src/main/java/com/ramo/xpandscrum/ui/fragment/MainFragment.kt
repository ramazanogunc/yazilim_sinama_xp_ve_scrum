package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.adapter.ProjectListAdapter
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.repository.ProjectRepository
import com.ramo.xpandscrum.viewModel.MainViewModel
import com.ramo.xpandscrum.viewModel.MainViewModelFactory

class MainFragment : Fragment(R.layout.fragment_main) {

    private val projectDao by lazy {
        AppDatabase.getInstance(requireContext()).projectDao
    }

    private val projectRepository by lazy {
        ProjectRepository(projectDao)
    }

    private val mainViewModel:MainViewModel by viewModels {
        MainViewModelFactory(projectRepository)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById< ExtendedFloatingActionButton>(R.id.fabNewProject).setOnClickListener { onClickAddProject() }
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerViewProjects)
        val adapter = ProjectListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mainViewModel.allProjects.observe(viewLifecycleOwner){ projectList ->
            projectList.let { adapter.submitList(it) }
        }

    }

    private fun onClickAddProject() {
        findNavController().navigate(R.id.action_mainFragment_to_addProjectFragment)
    }
}