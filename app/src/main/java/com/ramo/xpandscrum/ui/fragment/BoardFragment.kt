package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.databinding.FragmentBoardBinding
import com.ramo.xpandscrum.model.BoardType

class BoardFragment(private val projectId: Int, private val boardType: BoardType) :
    Fragment(R.layout.fragment_board) {

    private var boardBinding: FragmentBoardBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        boardBinding = FragmentBoardBinding.inflate(layoutInflater, container, false)
        return boardBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.test).text = boardType.name
    }


    override fun onDestroy() {
        super.onDestroy()
        boardBinding = null
    }
}