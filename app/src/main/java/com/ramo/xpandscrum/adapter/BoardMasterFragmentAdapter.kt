package com.ramo.xpandscrum.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ramo.xpandscrum.model.BoardType
import com.ramo.xpandscrum.ui.fragment.BoardFragment

class BoardMasterFragmentAdapter(fm: FragmentManager, private val projectId: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titleList = mutableListOf<String>()
    private val fragmentList = mutableListOf<BoardFragment>()

    override fun getCount(): Int = titleList.size

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getPageTitle(position: Int): CharSequence? = titleList[position]

    init {
        prepareTabs()
    }

    private fun prepareTabs() {
        setFragment("TO DO", BoardFragment(projectId, BoardType.TODO))
        setFragment("IN PROGRESS", BoardFragment(projectId, BoardType.IN_PROGRESS))
        setFragment("REVISION", BoardFragment(projectId, BoardType.REVISION))
        setFragment("CHECK", BoardFragment(projectId, BoardType.CHECK))
        setFragment("DONE", BoardFragment(projectId, BoardType.DONE))
    }

    private fun setFragment(string: String, boardFragment: BoardFragment) {
        titleList.add(string)
        fragmentList.add(boardFragment)
    }

}