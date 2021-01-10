package com.ramo.xpandscrum.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.repository.CardRepository
import com.ramo.xpandscrum.databinding.FragmentAddEditCardBinding
import com.ramo.xpandscrum.dateConvert
import com.ramo.xpandscrum.getCurrentDate
import com.ramo.xpandscrum.model.Card
import com.ramo.xpandscrum.predictDateFromDescription
import com.ramo.xpandscrum.showToast
import com.ramo.xpandscrum.viewModel.CardViewModel
import com.ramo.xpandscrum.viewModel.CardViewModelFactory
import java.util.*

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

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardId = number.cardId
        binding!!.btnSave.setOnClickListener { onSaveClick() }
        requireContext().showToast(cardId.toString())
        cardViewModel.getCard(cardId).observe(viewLifecycleOwner) {
            card = it
            getSetData(it)
        }
    }

    private fun onSaveClick() {
        val card = getDatFromUi()
        requireActivity().showToast(card.toString())
        cardViewModel.update(card)
        requireActivity().onBackPressed()
    }

    private fun getSetData(card: Card) {
        with(binding!!) {
            this.name.setText(card.name)
            this.description.setText(card.description)
            this.note.setText(card.note)
            this.date.setText(Date().getCurrentDate())
            this.predictDate.setText(card.predictedMinute.toString() + " Tahmini Süre(dakika)")
        }
    }

    private fun getDatFromUi(): Card {
        return card!!.apply {
            name = binding!!.name.text.toString()
            description = binding!!.description.text.toString()
            note = binding!!.note.text.toString()
            date = dateConvert()
            predictedMinute = predictDateFromDescription(binding!!.description.text.toString())
        }
    }

}