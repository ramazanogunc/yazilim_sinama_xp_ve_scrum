package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.util.Log
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
import com.ramo.xpandscrum.validateAndDo
import com.ramo.xpandscrum.viewModel.CardStatusViewModel
import com.ramo.xpandscrum.viewModel.CardStatusViewModelFactory
import java.util.*

// İş takibi ekleme güncelleme sayfası
class AddCardStatusFragment : Fragment() {

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
    // güncelleme halinde secili iş takibi id si
    private var cardStatusId: Int? = null
    // ilgili kart id si
    private var cardId = 0
    // spinnerdan seçilen kullanıcı id si
    private var selectedUserId = 0
    /*
    ekleme veya düzenleme durumu
    0 = Ekleme, 1 = düzenleme
     */
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

        if (mode == MODE_EDIT) {
            // get card status
            cardStatusViewModel.get(cardStatusId!!).observe(viewLifecycleOwner) { cardStatus ->
                cardStatus?.let { cardStatus->
                    setDataToUi(cardStatus)
                    selectedUserId = cardStatus.userId
                    // prepare spinner for selected user
                    cardStatusViewModel.allUsers.observe(viewLifecycleOwner) { userList ->
                        userList?.let { prepareSpinner(it) }
                    }
                }
            }
        }
        if (mode == MODE_ADD){
            cardStatusViewModel.allUsers.observe(viewLifecycleOwner) { userList ->
                userList?.let { prepareSpinner(it) }
            }
        }
    }

    // ui daki gerekli alanı doldurmak için kullanılan fonk
    private fun setDataToUi(cardStatus: CardStatus) {
        binding?.let {
            it.description.setText(cardStatus.description)
        }
    }

    // kullanıcıları spinner da sunmak için kullanılan fonk
    private fun prepareSpinner(userList: List<User>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            userList.map { user -> user.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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
                    //selectedUserId = 0
                }
            }
        setSpinnerSelectedUser(userList)
    }

    // spinerlerin içini doldurmak
    private fun setSpinnerSelectedUser(userList: List<User>){
        if (mode == MODE_EDIT && selectedUserId != 0) {
            Log.e("prepareSpinner:if", selectedUserId.toString())

            for (index in userList.indices) {
                if (userList[index].userId == selectedUserId)
                    binding!!.technicalPerson.setSelection(index)
            }
        }
    }

    // validasyon durumuna göre işlem yapılmaktadır
    private fun onBtnSaveClick() {

        if (cardId != 0 && selectedUserId != 0) {

            validateAndDo(
                listOf(
                    binding!!.description
                )
            ) {
                if (mode == MODE_ADD)
                    insert()
                else if (mode == MODE_EDIT)
                    update()
            }

        } else {
            requireContext().showToast("Eksikleri lütfen doldurun")
        }
    }

    // iş takibi ekleme durumu
    private fun insert() {
        val cardStatus = getCardStatusFromUi()
        cardStatusViewModel.insert(cardStatus)
        requireActivity().onBackPressed()
    }

    // iş takibi güncelleme durumu
    private fun update() {
        cardStatusId?.let {
            val cardStatus = getCardStatusFromUi()
            cardStatus.cardStatusId = it
            cardStatusViewModel.update(cardStatus)
            requireActivity().onBackPressed()
        }
    }

    // cardstatus döndüren fonk
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