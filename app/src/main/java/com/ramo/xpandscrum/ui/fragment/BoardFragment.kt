package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.adapter.CardListAdapter
import com.ramo.xpandscrum.calculateRealMinute
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.repository.CardRepository
import com.ramo.xpandscrum.databinding.FragmentBoardBinding
import com.ramo.xpandscrum.model.Card
import com.ramo.xpandscrum.model.CardType
import com.ramo.xpandscrum.viewModel.CardViewModel
import com.ramo.xpandscrum.viewModel.CardViewModelFactory

class BoardFragment(private val projectId: Int, private val cardType: CardType) :
    Fragment() {

    private var boardBinding: FragmentBoardBinding? = null

    private val cardViewModel: CardViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            CardViewModelFactory(CardRepository(AppDatabase.getInstance(requireContext()).cardDao))
        ).get(CardViewModel::class.java)
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

        val adapter = CardListAdapter(::onItemClick, ::onDeleteClick, ::onMoveClick)
        boardBinding?.let {
            it.recyclerViewBoard.adapter = adapter
            it.recyclerViewBoard.layoutManager = LinearLayoutManager(requireContext())
        }

        cardViewModel.getAllCardFromByProjectAndCardType(projectId, cardType)
            .observe(viewLifecycleOwner) { list ->
                adapter.submitList(list)
            }
    }

    // card ın gerekli type göre ayarlanması durumu
    private fun onMoveClick(card: Card, v: View) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(R.menu.menu_popup_move, popup.menu)
        val activeBoardMenuId = when (card.cardType) {
            CardType.TODO -> R.id.todo
            CardType.IN_PROGRESS -> R.id.inProgress
            CardType.REVISION -> R.id.revision
            CardType.CHECK -> R.id.check
            CardType.DONE -> R.id.done
        }
        activeBoardMenuId.let { popup.menu.findItem(it).isEnabled = false }

        // card tye değiştirmek için ekrana cıkan pop up
        popup.setOnMenuItemClickListener { menuItem ->
            card.cardType = when (menuItem?.itemId) {
                R.id.todo -> CardType.TODO
                R.id.inProgress -> CardType.IN_PROGRESS
                R.id.revision -> CardType.REVISION
                R.id.check -> CardType.CHECK
                R.id.done -> CardType.DONE
                else -> CardType.TODO
            }
            if (card.cardType == CardType.DONE) {
                card.realMinute = calculateRealMinute(card.date)
            }

            cardViewModel.update(card)
            true
        }
        popup.show()
    }

    private fun onDeleteClick(card: Card) {
        cardViewModel.delete(card.cardId)
    }

    private fun onItemClick(card: Card) {
        (parentFragment as BoardMasterFragment).navigateEditFragment(card.cardId)
    }

    override fun onDestroy() {
        super.onDestroy()
        boardBinding = null
    }
}