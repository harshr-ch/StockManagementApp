package com.harshit.stockmanagementapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import com.harshit.stockmanagementapp.R
import com.harshit.stockmanagementapp.model.ArrayModel
import com.harshit.stockmanagementapp.model.OrderHistoryModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderAdapter(
    val context: Context,
    private val itemList: ArrayList<OrderHistoryModel>
) :  RecyclerView.Adapter<OrderAdapter.OrderViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.history_custom_row, parent, false)

        return OrderAdapter.OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val menuObject = itemList[position]
        holder.txtDate.text = formatDate(menuObject.date)//menuObject.date
        setUpRecycler(holder.recyclerResHistory, menuObject.data)
    }

    private fun setUpRecycler(recyclerResHistory: RecyclerView, orderHistoryList: String) {
        val myType = object : TypeToken<List<ArrayModel>>() {}.type
        val logs = Gson().fromJson<List<ArrayModel>>(orderHistoryList, myType)
        //val arrayData: ArrayList<ArrayModel> = Gson().fromJson(orderHistoryList)
        val cartItemAdapter = ConfirmAdapter(logs as ArrayList<ArrayModel>,context)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerResHistory.layoutManager = mLayoutManager
        recyclerResHistory.itemAnimator = DefaultItemAnimator()
        recyclerResHistory.adapter = cartItemAdapter
    }
    private fun formatDate(dateString: String): String? {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date: Date = inputFormatter.parse(dateString) as Date


        val outputFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return outputFormatter.format(date)
    }

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDate : TextView = view.findViewById(R.id.txtDate)
        val recyclerResHistory: RecyclerView = view.findViewById(R.id.recyclerResHistoryItems)
    }

}

