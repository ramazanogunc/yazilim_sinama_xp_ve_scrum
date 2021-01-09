package com.ramo.xpandscrum.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

class AddCardFragment : Fragment() {

    private var projectId: Int = 0
    private var binding: FragmentAddEditCardBinding? = null

    private val cardViewModel : CardViewModel by viewModels  {
        CardViewModelFactory(
            CardRepository(AppDatabase.getInstance(requireContext()).cardDao)
        )
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
        projectId = arguments?.getInt("projectId")!!
        binding!!.btnSave.setOnClickListener { onSaveClick() }
        binding!!.date.setText(Date().getCurrentDate())
    }

    private fun onSaveClick() {
        val card = getDatFromUi()
        requireActivity().showToast(card.toString())
        cardViewModel.validateAndInsert(card){ error->
            requireContext().showToast(error)
        }
    }

    private fun getDatFromUi(): Card {
        return Card(
            binding!!.name.text.toString(),
            binding!!.description.text.toString(),
            binding!!.note.text.toString(),
            dateConvert(),
            predictDateFromDescription(binding!!.description.text.toString()),
            projectId
        )
    }

}