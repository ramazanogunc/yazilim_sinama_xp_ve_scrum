package com.ramo.xpandscrum.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ramo.xpandscrum.*
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.repository.CardRepository
import com.ramo.xpandscrum.database.repository.UserRepository
import com.ramo.xpandscrum.databinding.FragmentAddEditCardBinding
import com.ramo.xpandscrum.model.Card
import com.ramo.xpandscrum.viewModel.CardViewModel
import com.ramo.xpandscrum.viewModel.CardViewModelFactory
import com.ramo.xpandscrum.viewModel.UserViewModel
import com.ramo.xpandscrum.viewModel.UserViewModelFactory
import java.util.*

class AddEditCardFragment : Fragment() {

    private var projectId: Int = 0

    private var binding: FragmentAddEditCardBinding? = null

    private val cardViewModel: CardViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            CardViewModelFactory(CardRepository(AppDatabase.getInstance(requireContext()).cardDao))
        ).get(CardViewModel::class.java)
    }

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            UserViewModelFactory(UserRepository(AppDatabase.getInstance(requireContext()).userDao))
        ).get(UserViewModel::class.java)
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
        binding!!.predictDate.hide()
        binding!!.btnJobTrace.hide()

    }


    // kaybet butonunda text inputların kontolu durumunda gerekli işlemlerin yapılması
    private fun onSaveClick() {
        validateAndDo(
            listOf(
                binding!!.name,
                binding!!.description,
                binding!!.note
            )
        ) {
            val card = getDataFromUi()
            requireActivity().showToast(card.toString())
            requireActivity().onBackPressed()
            cardViewModel.validateAndInsert(card) { error ->
                requireContext().showToast(error)
            }
        }

    }

    // textlere girilen değerler ile card modelini doldurmak
    private fun getDataFromUi(): Card {
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