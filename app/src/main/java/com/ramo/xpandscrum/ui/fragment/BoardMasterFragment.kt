package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.adapter.BoardMasterFragmentAdapter

class BoardMasterFragment : Fragment(R.layout.fragment_master_board) {

    private var projectId: Int = 0
    private lateinit var projectName: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectId = arguments?.getInt("id")!!
        projectName = arguments?.getString("name")!!
        initUi()
        initTabLayout()

    }

    private fun initUi() {
        view?.findViewById<Toolbar>(R.id.toolbar)?.title = projectName
        view?.findViewById<ExtendedFloatingActionButton>(R.id.fabAddCard)
            ?.setOnClickListener { onFabClick() }
    }

    private fun initTabLayout() {
        val adapter = BoardMasterFragmentAdapter(childFragmentManager, projectId)
        val tabLayout = view?.findViewById<TabLayout>(R.id.tabs)
        val viewPager = view?.findViewById<ViewPager>(R.id.viewPager)
        viewPager?.adapter = adapter
        tabLayout?.setupWithViewPager(viewPager)
    }


    private fun onFabClick() {

    }

}