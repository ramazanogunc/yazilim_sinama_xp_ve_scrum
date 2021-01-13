package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramo.xpandscrum.MainActivity
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.adapter.ProjectListAdapter
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.repository.ProjectRepository
import com.ramo.xpandscrum.database.repository.UserRepository
import com.ramo.xpandscrum.databinding.FragmentMainBinding
import com.ramo.xpandscrum.getFakeUsers
import com.ramo.xpandscrum.model.Project
import com.ramo.xpandscrum.showToast
import com.ramo.xpandscrum.viewModel.MainViewModel
import com.ramo.xpandscrum.viewModel.MainViewModelFactory
import com.ramo.xpandscrum.viewModel.UserViewModel

class MainFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            ProjectRepository(AppDatabase.getInstance(requireContext()).projectDao)
        )
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

        val adapter = ProjectListAdapter(::onItemClick, ::onEditClick, ::onDeleteClick)
        with(mainBinding) {
            this!!.fabNewProject.setOnClickListener { onClickAddProject() }
            recyclerViewProjects.adapter = adapter
            recyclerViewProjects.layoutManager = LinearLayoutManager(requireContext())
            // for toolbar menu
            (requireActivity() as MainActivity).setSupportActionBar(toolbar)
            setHasOptionsMenu(true)
        }

        mainViewModel.allProjects.observe(viewLifecycleOwner) { projectList ->
            projectList.let { adapter.submitList(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fakeUsers -> {
                val userViewModel =
                    UserViewModel(UserRepository(AppDatabase.getInstance(requireContext()).userDao))
                val fakeUsers = getFakeUsers()
                fakeUsers.forEach { user ->
                    userViewModel.insert(user)
                }
                requireContext().showToast("Fake userlar veritabanına eklendi")

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onClickAddProject() {
        findNavController().navigate(R.id.action_mainFragment_to_addProjectFragment)
    }

    // tıklanan itemın project id sini bundle ile boardmastera aktarılması
    private fun onItemClick(project: Project) {
        val bundle = bundleOf("id" to project._id)
        bundle.putString("name", project.name)
        findNavController().navigate(R.id.action_mainFragment_to_boardMasterFragment, bundle)
    }

    // edit butonunda gerekli işlemlerin yapılması
    private fun onEditClick(project: Project) {
        val bundle = bundleOf("projectId" to project._id)
        bundle.putString("name", project.name)
        findNavController().navigate(R.id.action_mainFragment_to_editProjectFragment, bundle)
    }

    // delete butonuna tıklandığında database den veriyi silmesi
    private fun onDeleteClick(project: Project) {
        mainViewModel.delete(project._id)
    }
}