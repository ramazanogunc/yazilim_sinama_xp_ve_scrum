package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ramo.xpandscrum.database.AppDatabase
import com.ramo.xpandscrum.database.repository.CardStatusRepository
import com.ramo.xpandscrum.database.repository.UserRepository
import com.ramo.xpandscrum.databinding.FragmentAddEditCardStatusBinding
import com.ramo.xpandscrum.dateConvert
import com.ramo.xpandscrum.getCurrentDate
import com.ramo.xpandscrum.model.CardStatus
import com.ramo.xpandscrum.model.CardType
import com.ramo.xpandscrum.model.User
import com.ramo.xpandscrum.showToast
import com.ramo.xpandscrum.viewModel.CardStatusViewModel
import com.ramo.xpandscrum.viewModel.CardStatusViewModelFactory
import java.util.*

class AddEditCardStatusFragment : Fragment() {

    private var binding: FragmentAddEditCardStatusBinding? = null
    private val cardStatusViewModel: CardStatusViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            CardStatusViewModelFactory(
                CardStatusRepository(AppDatabase.getInstance(requireContext()).cardStatusDao),
                UserRepository(AppDatabase.getInstance(requireContext()).userDao)
            )
        ).get(CardStatusViewModel::class.java)
    }
    private var cardStatusId :Int? = null
    private var cardId = 0
    private var selectedUserId = 0
    private var mode = 0
    private val MODE_ADD = 0
    private val MODE_EDIT = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditCardStatusBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardId = arguments?.getInt("cardId")!!
        cardStatusId = arguments?.getInt("cardStatusId")!!
        mode = arguments?.getInt("mode")!!

        binding!!.date.setText(Date().getCurrentDate())
        binding!!.btnSave.setOnClickListener { onBtnSaveClick() }
        cardStatusViewModel.allUsers.observe(viewLifecycleOwner) { userList ->
            userList?.let { prepareSpinner(it) }
        }

        if (mode == MODE_EDIT){
            cardStatusViewModel.get(cardStatusId!!).observe(viewLifecycleOwner){ cardStatus ->
                cardStatus?.let { setDataToUi(cardStatus) }
            }
        }
    }

    private fun setDataToUi(cardStatus: CardStatus) {
        binding?.let {
            it.description.setText(cardStatus?.description)
        }
    }

    private fun prepareSpinner(userList: List<User>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            userList.map { user -> user.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding!!.technicalPerson.adapter = adapter
        binding!!.technicalPerson.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedUserId = userList[position].userId
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedUserId = 0
                }

            }
    }

    private fun onBtnSaveClick() {
        if (cardId != 0 && selectedUserId != 0) {
            if (mode == MODE_ADD)
                insert()
            else if (mode == MODE_EDIT)
                update()

        } else {
            requireContext().showToast("Eksikleri lütfen doldurun")
        }
    }

    private fun insert() {
        val cardStatus = getCardStatusFromUi()
        cardStatusViewModel.insert(cardStatus)
        requireActivity().onBackPressed()
    }

    private fun update() {
        cardStatusId?.let{
            val cardStatus = getCardStatusFromUi()
            cardStatus.cardStatusId = it
            cardStatusViewModel.update(cardStatus)
            requireActivity().onBackPressed()
        }
    }

    private fun getCardStatusFromUi(): CardStatus {
        return CardStatus(
            cardId,
            selectedUserId,
            binding!!.description.text.toString(),
            arguments?.get("cardType") as CardType,
            dateConvert()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}