package com.harshit.stockmanagementapp.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.harshit.stockmanagementapp.R
import com.harshit.stockmanagementapp.model.UserModel

class BuyItemAdapter(private var item: ArrayList<UserModel>, val context: Context, var map : HashMap<String,Int>) :
    RecyclerView.Adapter<BuyItemAdapter.BuyItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.buy_custom_row, parent, false)

        return BuyItemViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: BuyItemViewHolder, position: Int) {
        val itemObject = item.get(position)
        holder.itemName.text = itemObject.name
        holder.itemCost.text = "Rs. " + itemObject.price + "/-"
        val s = itemObject.image.size.let { it1 -> BitmapFactory.decodeByteArray(itemObject.image,0, it1) }
        holder.itemImage.setImageBitmap(s)

        holder.increment.setOnClickListener {
            var x = map[itemObject.name]
            if (x != null) {
                x=x+1
            }
            if (x != null && x<=itemObject.quantity.toInt()) {
                map[itemObject.name] = x.toInt()
            }
            else
                Toast.makeText(context,"Maxed Out",Toast.LENGTH_SHORT).show()
            holder.quantity.text = map[itemObject.name].toString()
        }

        holder.decrement.setOnClickListener {
            var y = map[itemObject.name]
            if (y != null) {
                y= y - 1
            }
            if (y != null && y>=0) {
                map[itemObject.name] = y.toInt()
            }
            else
                Toast.makeText(context,"Cant go below zero",Toast.LENGTH_SHORT).show()
            holder.quantity.text = map[itemObject.name].toString()
        }
    }

    class BuyItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage = view.findViewById(R.id.imgItem) as ImageView
        val itemName = view.findViewById(R.id.txtItemName) as TextView
        val itemCost = view.findViewById(R.id.txtItemCost) as TextView
        val increment = view.findViewById(R.id.increment) as Button
        val quantity = view.findViewById(R.id.quantity) as TextView
        val decrement = view.findViewById(R.id.decrement) as Button
        val cardItem = view.findViewById(R.id.cardItem) as CardView
    }
}