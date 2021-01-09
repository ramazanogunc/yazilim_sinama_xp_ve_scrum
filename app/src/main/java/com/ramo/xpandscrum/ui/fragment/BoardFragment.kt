package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramo.xpandscrum.adapter.CardListAdapter
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.repository.CardRepository
import com.ramo.xpandscrum.databinding.FragmentBoardBinding
import com.ramo.xpandscrum.model.Card
import com.ramo.xpandscrum.model.CardType
import com.ramo.xpandscrum.showToast
import com.ramo.xpandscrum.viewModel.CardViewModel
import com.ramo.xpandscrum.viewModel.CardViewModelFactory

class BoardFragment(private val projectId: Int, private val cardType: CardType) :
    Fragment() {

    private var boardBinding: FragmentBoardBinding? = null

    private val cardViewModel: CardViewModel by viewModels {
        CardViewModelFactory(
            CardRepository(AppDatabase.getInstance(requireContext()).cardDao)
        )
    }

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

        val adapter = CardListAdapter(::onItemClick, ::onEditClick, ::onMoveClick)
        boardBinding?.let {
            it.recyclerViewBoard.adapter = adapter
            it.recyclerViewBoard.layoutManager = LinearLayoutManager(requireContext())
        }

        cardViewModel.getAllCardFromByProjectAndCardType(projectId, cardType)
            .observe(viewLifecycleOwner) { list ->
                list.let { adapter.submitList(it) }
            }
    }

    private fun onMoveClick(card: Card) {
        requireContext().showToast(card.cardId.toString())
    }

    private fun onEditClick(card: Card) {
        (parentFragment as BoardMasterFragment).navigateEditFragment(card.cardId)
    }

    private fun onItemClick(card: Card) {

    }

    override fun onDestroy() {
        super.onDestroy()
        boardBinding = null
    }
}