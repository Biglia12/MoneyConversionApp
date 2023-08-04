package com.kotlin.moneyconversionapp.ui.view.fragments.SettingsModule

import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.ui.adapters.SettingsModule.SettingsAdapter
import com.kotlin.moneyconversionapp.data.model.settings.SettingsModel
import com.kotlin.moneyconversionapp.databinding.FragmentSettingsBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.Setting.SettingsViewModel
import javax.inject.Inject

class SettingsFragment @Inject constructor() : Fragment(), InterfaceSettings {

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

        //val settingTheme = SettingsModel(R.drawable.ic_bitcoin_color, requireContext().resources.getString(R.string.choose_theme)) // se vera que ahcer con el cambio de tema ya que hay que pensar en los colores en cada elemento UI
        val settingShareApp = SettingsModel(
            R.drawable.ic_baseline_share_24,
            requireContext().resources.getString(R.string.share_app)
        )
        val settingContact = SettingsModel(
            R.drawable.ic_email,
            requireContext().resources.getString(R.string.contact)
        )
        val settingReview = SettingsModel(
            R.drawable.ic_rate_review,
            requireContext().resources.getString(R.string.review_playstore)
        )

        val listSettings = listOf(
            //settingTheme,
            settingShareApp,
            settingContact,
            settingReview
        )

        adapter = SettingsAdapter(
            listSettings,
            requireContext(),
            this
        )
        binding.recyclerSettings.layoutManager = LinearLayoutManager(context)
        binding.recyclerSettings.adapter = adapter
    }


    override fun shareApp() {

        val appPackageName = requireActivity().packageName
        val playStoreUrl = "https://play.google.com/store/apps/details?id=$appPackageName"
        val messageToShare = requireContext().resources.getString(R.string.share_message)
        val downloadAppText = requireContext().resources.getString(R.string.download_app)

        val message = "$messageToShare \n\n$downloadAppText$playStoreUrl"

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "DólarArg")
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(shareIntent, "Descargar app"))

    }

    override fun openMail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("sfeal12@gmail.com"))
        //intent.setPackage("com.google.android.gm")
        requireContext().startActivity(intent)
    }
}
