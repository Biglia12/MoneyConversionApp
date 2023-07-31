package com.kotlin.moneyconversionapp.ui.view.fragments.SettingsModule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.adapters.SettingsAdapter
import com.kotlin.moneyconversionapp.data.model.settings.SettingsModel
import com.kotlin.moneyconversionapp.databinding.FragmentSettingsBinding
import javax.inject.Inject

class SettingsFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SettingsAdapter

    //private var listSettings = ArrayList<SettingsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

    }

    private fun initRecyclerView() {

        val settingShareApp = SettingsModel(resources.getDrawable(R.drawable.ic_dollar_icon), "compartir app")
        val settingTheme = SettingsModel(resources.getDrawable(R.drawable.ic_dollar_icon), "elegir tema")
        val settingContact = SettingsModel(resources.getDrawable(R.drawable.ic_dollar_icon), "Contactar")
        val settingReview = SettingsModel(resources.getDrawable(R.drawable.ic_dollar_icon), "Dejar Reseña")
       // var settingShare: SettingsModel = SettingsModel(resources.getDrawable(R.drawable.ic_dollar_icon), "dasdsa")
       // var settingShare: SettingsModel = SettingsModel(resources.getDrawable(R.drawable.ic_dollar_icon), "dasdsa")

        val listSettings = listOf(
            settingShareApp,
            settingTheme,
            settingContact,
            settingReview)



        adapter = SettingsAdapter(
            listSettings,
            requireContext()
        )
        binding.recyclerSettings.layoutManager = LinearLayoutManager(context)
        binding.recyclerSettings.adapter = adapter
    }
}
