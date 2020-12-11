package com.harshit.stockmanagementapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harshit.stockmanagementapp.R
import com.harshit.stockmanagementapp.model.ArrayModel

class ConfirmAdapter(private var item: ArrayList<ArrayModel>, val context: Context):RecyclerView.Adapter<ConfirmAdapter.ConfirmItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfirmItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.confirm_custom_row, parent, false)

        return ConfirmItemViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ConfirmItemViewHolder, position: Int) {
        val itemObject = item.get(position)
        holder.itemName.text = itemObject.name
        holder.itemCost.text = "Rs. " + itemObject.price
        holder.itemQuantity.text = itemObject.quantity
    }

    class ConfirmItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName = view.findViewById(R.id.txtItemName) as TextView
        val itemCost = view.findViewById(R.id.txtItemCost) as TextView
        val itemQuantity = view.findViewById(R.id.txtItemQuantity) as TextView
    }

}