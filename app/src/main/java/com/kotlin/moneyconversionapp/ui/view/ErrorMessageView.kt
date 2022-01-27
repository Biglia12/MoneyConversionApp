package com.kotlin.moneyconversionapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ActivityErrorMessageViewBinding

class ErrorMessageView : Fragment() {

    private var _binding: ActivityErrorMessageViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityErrorMessageViewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun errorMessageService(actionListener: View.OnClickListener) {
        binding.txtErrorMesagge.text = R.string.error_message.toString()
        binding.magCartFragmentButton.isClickable = true
        binding.magCartFragmentButton.setOnClickListener(actionListener)
    }
}