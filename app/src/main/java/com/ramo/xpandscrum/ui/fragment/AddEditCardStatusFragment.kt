package com.ramo.xpandscrum.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ramo.xpandscrum.databinding.FragmentAddEditCardStatusBinding

class AddEditCardStatusFragment: Fragment() {

    private var binding: FragmentAddEditCardStatusBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditCardStatusBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}