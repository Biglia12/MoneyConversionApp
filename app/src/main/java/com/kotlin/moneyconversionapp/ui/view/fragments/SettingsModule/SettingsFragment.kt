package com.kotlin.moneyconversionapp.ui.view.fragments.SettingsModule

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.adapters.SettingsAdapter
import com.kotlin.moneyconversionapp.data.model.settings.SettingsModel
import com.kotlin.moneyconversionapp.databinding.FragmentSettingsBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.Setting.SettingsViewModel
import javax.inject.Inject

class SettingsFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val settingViewModel: SettingsViewModel by viewModels()

    private lateinit var adapter: SettingsAdapter


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

        val settingTheme = SettingsModel(R.drawable.ic_bitcoin_color, requireContext().resources.getString(R.string.choose_theme))
        val settingShareApp = SettingsModel(R.drawable.ic_baseline_share_24,  requireContext().resources.getString(R.string.share_app))
        val settingContact = SettingsModel(R.drawable.ic_email, requireContext().resources.getString(R.string.contact))
        val settingReview = SettingsModel(R.drawable.ic_rate_review, requireContext().resources.getString(R.string.review_playstore))

        val listSettings = listOf(
            settingTheme,
            settingShareApp,
            settingContact,
            settingReview
        )

        adapter = SettingsAdapter(
            listSettings,
            requireContext()
        )
        binding.recyclerSettings.layoutManager = LinearLayoutManager(context)
        binding.recyclerSettings.adapter = adapter
    }
}
