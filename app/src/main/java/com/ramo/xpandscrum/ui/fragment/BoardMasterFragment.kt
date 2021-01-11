package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.adapter.BoardMasterFragmentAdapter
import com.ramo.xpandscrum.databinding.FragmentMasterBoardBinding

class BoardMasterFragment : Fragment() {

    private var projectId: Int = 0
    private lateinit var projectName: String

    private var masterBoardBinding: FragmentMasterBoardBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        masterBoardBinding = FragmentMasterBoardBinding.inflate(layoutInflater, container, false)
        return masterBoardBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectId = arguments?.getInt("id")!!
        projectName = arguments?.getString("name")!!
        initUi()
        initTabLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        masterBoardBinding = null
    }

    fun navigateEditFragment(cardId: Int) {
        findNavController().navigate(
            BoardMasterFragmentDirections.actionBoardMasterFragmentToEditCardFragment2(
                cardId
            )
        )
    }


    private fun initUi() {
        with(masterBoardBinding) {
            this!!.toolbar.title = projectName
            fabAddCard.setOnClickListener { onFabClick() }
        }
    }

    private fun initTabLayout() {
        val adapter = BoardMasterFragmentAdapter(childFragmentManager, projectId)
        with(masterBoardBinding) {
            this!!.viewPager.adapter = adapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun onFabClick() {
        val bundle = bundleOf("projectId" to projectId)
        findNavController().navigate(R.id.action_boardMasterFragment_to_addCardFragment, bundle)
    }

}