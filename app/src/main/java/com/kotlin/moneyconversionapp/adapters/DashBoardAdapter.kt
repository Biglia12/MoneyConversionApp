package com.kotlin.moneyconversionapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ItemDashboardBinding
import com.kotlin.moneyconversionapp.model.CasaResponse

class DashBoardAdapter(private val dollarList: List<CasaResponse>): RecyclerView.Adapter<DashBoardAdapter.DashBoardHolder>() {

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


    class DashBoardHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemDashboardBinding.bind(view)

        fun bind(dollar: CasaResponse){
            binding.txtTypeDollar.text = dollar.dollarCasa.nombre
            binding.txtSellMoney.text = dollar.dollarCasa.venta
            binding.txtBuyMoney.text = dollar.dollarCasa.compra
            binding.textViewVariation.text = dollar.dollarCasa.variacion
        }
    }
}