package com.kotlin.moneyconversionapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.data.model.settings.SettingsModel
import com.kotlin.moneyconversionapp.databinding.SettingsAdapterBinding


class SettingsAdapter(
    private val listSettings: List<SettingsModel>,
    private val context: Context
) :
    RecyclerView.Adapter<SettingsAdapter.SettingHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SettingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SettingHolder(
            layoutInflater.inflate(
                R.layout.settings_adapter,
                parent,
                false
            ),

            )
    }


    override fun onBindViewHolder(holder: SettingHolder, position: Int) {
        val item = listSettings[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listSettings.size

    class SettingHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = SettingsAdapterBinding.bind(view)
        fun bind(dollar: SettingsModel) {
            binding.imageViewSettings.setImageResource(dollar.image)
            binding.imageViewSettings.setColorFilter(binding.root.context.resources.getColor(R.color.darkGreen))
            binding.TextViewSettings.text = dollar.title

            clickItems()

        }


        private fun clickItems() {
            binding.itemOptionSettings.setOnClickListener {
                when (binding.TextViewSettings.text) {
                    binding.root.context.resources.getString(R.string.choose_theme) -> chooseTheme() //Toast.makeText(binding.root.context, "elegir tema", Toast.LENGTH_SHORT).show()
                    binding.root.context.resources.getString(R.string.share_app) -> Toast.makeText(
                        binding.root.context,
                        "compartir app",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.root.context.resources.getString(R.string.contact) -> Toast.makeText(
                        binding.root.context,
                        "Contactar",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.root.context.resources.getString(R.string.review_playstore) -> Toast.makeText(
                        binding.root.context,
                        "reseña",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        private fun chooseTheme() {

            val fonts = arrayOf(
                "Dark", "Light", "System"
            )

            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("Select a text size")
            builder.setItems(fonts) { dialog, which ->
                when (fonts[which]) {
                    "Dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            builder.show()
        }

    }

}

