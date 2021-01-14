package com.ramo.xpandscrum.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ramo.xpandscrum.model.CardType
import com.ramo.xpandscrum.ui.fragment.BoardFragment

/*
Tahtalarda kullanılan tablayout için hazırlandır.
görevi tabları oluşturmak ve yönetmek

 */
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

    // tabları hazırlar
    private fun prepareTabs() {
        setFragment("TO DO", BoardFragment(projectId, CardType.TODO))
        setFragment("IN PROGRESS", BoardFragment(projectId, CardType.IN_PROGRESS))
        setFragment("REVISION", BoardFragment(projectId, CardType.REVISION))
        setFragment("CHECK", BoardFragment(projectId, CardType.CHECK))
        setFragment("DONE", BoardFragment(projectId, CardType.DONE))
    }

    // bir tane tabı set eder
    private fun setFragment(string: String, boardFragment: BoardFragment) {
        titleList.add(string)
        fragmentList.add(boardFragment)
    }

}