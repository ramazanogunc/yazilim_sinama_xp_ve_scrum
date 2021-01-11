package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramo.xpandscrum.R
import com.ramo.xpandscrum.adapter.CardStatusListAdaper
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.repository.CardStatusRepository
import com.ramo.xpandscrum.databinding.FragmentCardStatusBinding
import com.ramo.xpandscrum.model.CardStatus
import com.ramo.xpandscrum.viewModel.CardStatusViewModel
import com.ramo.xpandscrum.viewModel.CardStatusViewModelFactory

class CardStatusFragment: Fragment() {

    var binding: FragmentCardStatusBinding? = null
    var cardId: Int = 0
    private val cardStatusViewModel: CardStatusViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            CardStatusViewModelFactory(CardStatusRepository(AppDatabase.getInstance(requireContext()).cardStatusDao))
        ).get(CardStatusViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardStatusBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardId = arguments?.getInt("cardId")!!

        val adapter = CardStatusListAdaper(::onItemClick, ::onDeleteClick)
        with(binding) {
            this!!.fabNewCardStatus.setOnClickListener { onClickAddCardStatus() }
            recyclerViewCardStatus.adapter = adapter
            recyclerViewCardStatus.layoutManager = LinearLayoutManager(requireContext())
        }

        cardStatusViewModel.getAllCardStatusCardId(cardId).observe(viewLifecycleOwner) { cardList ->
            cardList.let { adapter.submitList(it) }
        }
    }


    private fun onItemClick(cardStatus: CardStatus) {

    }

    private fun onDeleteClick(cardStatus: CardStatus) {

    }

    private fun onClickAddCardStatus() {
        val bundle = bundleOf("cardId" to cardId )
        findNavController().navigate(R.id.action_cardStatusFragment_to_addEditCardStatusFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}