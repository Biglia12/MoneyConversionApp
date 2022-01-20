package com.kotlin.moneyconversionapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ItemDashboardBinding
import com.kotlin.moneyconversionapp.model.DollarResponse

class DashBoardAdapter(private val pokemonList: List<DollarResponse>): RecyclerView.Adapter<DashBoardAdapter.DashBoardHolder>() {

    override fun onBindViewHolder(holder: DashBoardHolder, position: Int) {
        val item = pokemonList[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DashBoardHolder(layoutInflater.inflate(R.layout.item_dashboard, parent, false))
    }

    override fun getItemCount(): Int =
        pokemonList.size



    class DashBoardHolder(val view: View): RecyclerView.ViewHolder(view){
        val binding = ItemDashboardBinding.bind(view)

        fun bind(pokemon: DollarResponse){
            binding.txtBuyMoney.text = pokemon.name
            binding.txtSellMoney.text = pokemon.type
        }
    }
}