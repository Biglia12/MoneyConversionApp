package com.kotlin.moneyconversionapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.data.model.settings.SettingsModel
import com.kotlin.moneyconversionapp.databinding.ItemDashboardBinding
import com.kotlin.moneyconversionapp.databinding.SettingsAdapterBinding
import com.kotlin.moneyconversionapp.utils.InterfaceAppUpdate

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
            )
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

            binding.imageViewSettings.setImageDrawable(dollar.image)
            binding.TextViewSettings.text = dollar.title

        }

    }

}

