package com.kotlin.moneyconversionapp.ui.view.fragments.SettingsModule

import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.data.model.settings.SettingsModel
import com.kotlin.moneyconversionapp.databinding.FragmentSettingsBinding
import com.kotlin.moneyconversionapp.ui.adapters.SettingsModule.SettingsAdapter
import com.kotlin.moneyconversionapp.ui.viewmodel.Setting.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment @Inject constructor() : Fragment(), InterfaceSettings {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val settingViewModel: SettingsViewModel by viewModels()

    private lateinit var adapter: SettingsAdapter

    private lateinit var  appPackageName: String
    private lateinit var  playStoreUrl: String
    private lateinit var  versionName: String


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

        initStrings()
        initRecyclerView()
        binding.textViewVersion.text = versionName

    }

    private fun initStrings() {
        appPackageName = requireActivity().packageName
        playStoreUrl = Constants.GOOGLE_PLAY + appPackageName

        val pInfo: PackageInfo? = requireActivity().packageManager.getPackageInfo(requireActivity().packageName, 0);
        versionName = pInfo?.versionName.toString()
    }

    private fun initRecyclerView() {

        //val settingTheme = SettingsModel(R.drawable.ic_bitcoin_color, requireContext().resources.getString(R.string.choose_theme)) // se vera que ahcer con el cambio de tema ya que hay que pensar en los colores en cada elemento UI
        val settingShareApp = SettingsModel(
            R.drawable.ic_baseline_share_24,
            requireContext().resources.getString(R.string.share_app)
        )
        val settingShareQr = SettingsModel(
            R.drawable.ic_qr,
            requireContext().resources.getString(R.string.share_app_qr)
        )
        val settingContact = SettingsModel(
            R.drawable.ic_email,
            requireContext().resources.getString(R.string.contact)
        )
        val settingReview = SettingsModel(
            R.drawable.ic_star,
            requireContext().resources.getString(R.string.review_playstore)
        )

        val about = SettingsModel(
            R.drawable.ic_about,
            requireContext().resources.getString(R.string.about)
        )

        val listSettings = listOf(
            //settingTheme,
            settingShareApp,
            settingShareQr,
            settingContact,
            settingReview,
            about
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
        val messageToShare = requireContext().resources.getString(R.string.share_message)
        val downloadAppText = requireContext().resources.getString(R.string.download_app)

        val message = "$messageToShare \n\n$downloadAppText$playStoreUrl"

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "DólarArg")
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(shareIntent, "Descargar app"))

    }

    override fun shareAppQr() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.qr_view, null)
        val qrImageView: ImageView = dialogView.findViewById(R.id.qrImageView)

        settingViewModel.generateQr(playStoreUrl)
        settingViewModel.qrCodeImage.observe(viewLifecycleOwner) {
            qrImageView.setImageBitmap(it)
        }

        builder.setView(dialogView)
        builder.setCancelable(true)
        val dialog = builder.create()
        dialog.show()
    }

    override fun openGooglePlay() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (e: Exception) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(playStoreUrl)
                )
            )
        }
    }

    override fun openMail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("sfeal12@gmail.com"))
        requireContext().startActivity(intent)
    }

    override fun openDialogAbout() {

        val messageGooglePlay =
            requireContext().resources.getString(R.string.message_like_google_play)

        val aboutMessage = requireContext().resources.getString(R.string.thanks_message)
        val start = 0
        val end: Int = aboutMessage.length

        val fancySentence = SpannableStringBuilder(aboutMessage)
        fancySentence.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val cDialog =
            AlertDialog.Builder(requireContext(), R.style.Theme_MaterialComponents_Dialog_Alert)
                .setTitle(requireContext().resources.getString(R.string.about))
                .setMessage(TextUtils.concat(fancySentence, "\r\n\n$messageGooglePlay"))
                .setPositiveButton(requireContext().resources.getString(R.string.acept)) { dialog, _ -> dialog.dismiss() }
                .show()
        val colorPrimary = requireContext().getColor(R.color.colorPrimary)
        cDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorPrimary)
    }

}
