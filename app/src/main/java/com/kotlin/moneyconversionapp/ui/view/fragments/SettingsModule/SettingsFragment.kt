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

        val newIconColor = ContextCompat.getColor(requireContext(), R.color.black)

        //settingViewModel.changeIconColor()

        val settingShareApp = SettingsModel(changeIconColor(AppCompatResources.getDrawable(requireContext(),R.drawable.ic_baseline_share_24)!!, R.color.black), "compartir app")
        val settingTheme = SettingsModel(resources.getDrawable(R.drawable.ic_dollar_icon), "elegir tema")
        val settingContact = SettingsModel(resources.getDrawable(R.drawable.ic_email), "Contactar")
        val settingReview = SettingsModel(resources.getDrawable(R.drawable.ic_rate_review), "Dejar Reseña")
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

    // Crear un método para cambiar el color del ícono
    fun changeIconColor(drawable: Drawable, color: Int): Drawable {
            val wrappedDrawable = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(wrappedDrawable, color)
            return wrappedDrawable
    }
}
