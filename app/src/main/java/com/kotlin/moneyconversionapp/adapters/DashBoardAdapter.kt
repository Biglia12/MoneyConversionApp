package com.kotlin.moneyconversionapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ItemDashboardBinding
import com.kotlin.moneyconversionapp.data.model.CasaResponse

class DashBoardAdapter(private val dollarList: ArrayList<CasaResponse>) :
    RecyclerView.Adapter<DashBoardAdapter.DashBoardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DashBoardHolder(layoutInflater.inflate(R.layout.item_dashboard, parent, false))
    }

    override fun getItemCount(): Int =
        dollarList.size

    override fun onBindViewHolder(holder: DashBoardHolder, position: Int) {
        val item = dollarList[position]
        holder.bind(item)

    }

    fun removeItem(position: Int) {
        dollarList.removeAt(position)
        notifyItemRemoved(position)
    }


    class DashBoardHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDashboardBinding.bind(view)

        fun bind(dollar: CasaResponse) {
            val sellMoney = "$" + dollar.dollarCasa.venta
            val buyMoney = "$" + dollar.dollarCasa.compra

            binding.txtTypeDollar.text = dollar.dollarCasa.nombre
            binding.txtSellMoney.text = sellMoney
            binding.txtBuyMoney.text = buyMoney
            binding.textViewVariation.text = dollar.dollarCasa.variacion
            binding.imageViewCountry.setImageResource(R.drawable.ic_bitcoin_color)

            if (dollar.dollarCasa.nombre?.startsWith("Dolar") == true) {
                binding.imageViewCountry.setImageResource(R.drawable.ic_dollar_icon)
            }

            if (dollar.dollarCasa.nombre.isNullOrEmpty()) {
                changeText(binding.txtTypeDollar)
            }

            if (dollar.dollarCasa.venta.isNullOrEmpty()) {
                changeText(binding.txtSellMoney)
            }

            if (dollar.dollarCasa.compra.isNullOrEmpty()) {
                changeText(binding.txtBuyMoney)
            }

            if (dollar.dollarCasa.variacion.isNullOrEmpty()) {
                changeText(binding.textViewVariation)
            }
        }

        private fun changeText(text: TextView) {
            text.text = "No hay datos"
        }

    }
}