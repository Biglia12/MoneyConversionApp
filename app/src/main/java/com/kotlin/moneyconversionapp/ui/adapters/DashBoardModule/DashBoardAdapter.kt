package com.kotlin.moneyconversionapp.ui.adapters.DashBoardModule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ItemDashboardBinding
import com.kotlin.moneyconversionapp.domain.model.DollarCasa

class DashBoardAdapter(private val dollarList: List<DollarCasa>, private val context: Context) :
    RecyclerView.Adapter<DashBoardAdapter.DashBoardHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DashBoardHolder(layoutInflater.inflate(R.layout.item_dashboard, parent, false))
    }

    override fun getItemCount(): Int =
        dollarList.size

    override fun onBindViewHolder(holder: DashBoardHolder, position: Int) {
        val item = dollarList[position]
        holder.bind(item, context)

    }

   /* fun removeItem(position: Int) {
        dollarList.removeAt(position)
        notifyItemRemoved(position)
    }*/


    class DashBoardHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemDashboardBinding.bind(view)

        fun bind(dollar: DollarCasa, context: Context) {
            var sellMoney = "$" + dollar.venta
            var buyMoney = "$" + dollar.compra


            if (buyMoney.contains("No Cotiza")) {
                buyMoney = removeSignDolla(buyMoney)
            }

            if (sellMoney.contains("No Cotiza") || sellMoney.contains("{}")){
                sellMoney = removeSignDolla(sellMoney)
            }

            binding.txtTypeDollar.text = dollar.nombre
            binding.txtSellMoney.text = sellMoney
            binding.txtBuyMoney.text = buyMoney
            binding.textViewVariation.text = dollar.variacion.toString()
            binding.imageViewCountry.setImageResource(R.drawable.ic_bitcoin_color)

            if (dollar.nombre?.startsWith("Dolar") == true) {
                binding.imageViewCountry.setImageResource(R.drawable.ic_dollar_icon)
            }

            when {
                dollar.nombre.isNullOrEmpty() -> changeText(binding.txtTypeDollar)
                dollar.venta.toString().isNullOrEmpty() || dollar.venta.toString() == "null" ||  dollar.venta.toString() == "{}"-> changeText(binding.txtSellMoney)
                dollar.compra.isNullOrEmpty() -> changeText(binding.txtBuyMoney)
                dollar.variacion.toString().isNullOrEmpty() || dollar.variacion.toString() == "null"||  dollar.variacion.toString() == "{}" -> changeText(binding.textViewVariation) // lo se es horrible esto pero al ser un servicio de terceros no hay muchas formas de solucionarlo ya que a veces el json viene con diferentes tipos de valores y no siempre es un string
                dollar.variacion.toString().contains("-") -> binding.textViewVariation.setTextColor(ContextCompat.getColor(context,R.color.red))
            }

        }

        private fun removeSignDolla(text: String): String {
            return text.replace("$", "")
        }

        private fun changeText(text: TextView) {
            text.text = "No hay datos"
        }

    }
}