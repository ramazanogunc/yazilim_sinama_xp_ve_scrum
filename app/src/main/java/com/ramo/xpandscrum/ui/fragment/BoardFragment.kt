package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.model.BoardType

class BoardFragment(private val projectId: Int, private val boardType: BoardType) :
    Fragment(R.layout.fragment_board) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.test).text = boardType.name
    }
}