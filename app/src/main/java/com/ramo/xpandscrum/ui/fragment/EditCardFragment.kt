package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ramo.xpandscrum.*
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.repository.CardRepository
import com.ramo.xpandscrum.databinding.FragmentAddEditCardBinding
import com.ramo.xpandscrum.model.Card
import com.ramo.xpandscrum.viewModel.CardViewModel
import com.ramo.xpandscrum.viewModel.CardViewModelFactory

class EditCardFragment : Fragment() {

    private var cardId: Int = 0
    private var binding: FragmentAddEditCardBinding? = null
    private val number by navArgs<EditCardFragmentArgs>()
    private var card: Card? = null

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
    ): View? {
        binding = FragmentAddEditCardBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardId = number.cardId
        binding!!.btnSave.setOnClickListener { onSaveClick() }
        binding!!.btnJobTrace.setOnClickListener { onJobTraceClick() }
        cardViewModel.getCard(cardId).observe(viewLifecycleOwner) {
            it.let {
                card = it
                setData(it)
            }
        }
    }

    private fun onJobTraceClick() {
        val bundle = bundleOf("cardId" to cardId)
        bundle.putSerializable("cardType", card?.cardType)
        findNavController().navigate(R.id.action_editCardFragment_to_cardStatusFragment, bundle)
    }


    private fun onSaveClick() {

        validateAndDo(
            listOf(
                binding!!.name,
                binding!!.description,
                binding!!.note
            )
        ) {
            val card = getDatFromUi()
            requireActivity().showToast(card.toString())
            cardViewModel.update(card)
            requireActivity().onBackPressed()
        }
    }

    //  card bilgilerini doldurmak
    private fun setData(card: Card) {
        with(binding!!) {
            this.name.setText(card.name)
            this.description.setText(card.description)
            this.note.setText(card.note)
            this.date.setText(card.date.getCurrentDate())
            this.predictDate.setText(card.predictedMinute.toString())
            if (card.realMinute != null)
                this.realMinute.setText(card.realMinute.toString())
        }
    }

    // doldurulan datayı alıp card a eşitlemek
    private fun getDatFromUi(): Card {
        return card!!.apply {
            name = binding!!.name.text.toString()
            description = binding!!.description.text.toString()
            note = binding!!.note.text.toString()
            predictedMinute = predictDateFromDescription(binding!!.description.text.toString())
        }
    }

}