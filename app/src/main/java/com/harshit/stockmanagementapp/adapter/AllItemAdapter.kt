package com.harshit.stockmanagementapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.harshit.stockmanagementapp.R
import com.harshit.stockmanagementapp.activity.Inventory2
import com.harshit.stockmanagementapp.model.UserModel
import com.harshit.stockmanagementapp.database.UsersDBHelper

class AllItemAdapter(private var item: ArrayList<UserModel>, val context: Context) :
    RecyclerView.Adapter<AllItemAdapter.AllItemViewHolder>() {
    lateinit var usersDBHelper: UsersDBHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_custom_row, parent, false)
        usersDBHelper =
            UsersDBHelper(context)

        return AllItemViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: AllItemViewHolder, position: Int) {
        val itemObject = item.get(position)
        holder.itemName.text = itemObject.name
        holder.itemCost.text = "Rs. " + itemObject.price + "/-"
        val s = itemObject.image.size.let { it1 ->
            BitmapFactory.decodeByteArray(
                itemObject.image,
                0,
                it1
            )
        }
        holder.itemImage.setImageBitmap(s)
        holder.cardItem.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_editText)

            with(builder) {
                setTitle("Enter Quantity ->")
                setPositiveButton("OK") { dialogInterface, i ->
                    if (editText.text.toString().length > 0) {
                        usersDBHelper.update1(itemObject.name, editText.text.toString())
                        context.startActivity(Intent(context, Inventory2::class.java))
                    }
                }
                setNegativeButton("Cancel") { dialogInterface, i ->
                }
                setView(dialogLayout)
                show()
            }
        }
        holder.itemQuantity.text = itemObject.quantity
        holder.cardItem.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("DELETE")
            builder.setMessage("Are you sure you want to delete this item ?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                usersDBHelper.deleteUser(itemObject.name)
                context.startActivity(Intent(context,
                    Inventory2::class.java))
            }
            builder.setNegativeButton("No"){dialogInterface, which ->

            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
            return@setOnLongClickListener true
        }
    }

    class AllItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage = view.findViewById(R.id.imgItem) as ImageView
        val itemName = view.findViewById(R.id.txtItemName) as TextView
        val itemQuantity = view.findViewById(R.id.txtItemQuantity) as TextView
        val itemCost = view.findViewById(R.id.txtItemCost) as TextView
        val cardItem = view.findViewById(R.id.cardItem) as CardView
    }
}