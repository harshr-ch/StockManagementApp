package com.harshit.stockmanagementapp.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harshit.stockmanagementapp.R
import com.harshit.stockmanagementapp.adapter.AllItemAdapter
import com.harshit.stockmanagementapp.adapter.OrderAdapter
import com.harshit.stockmanagementapp.database.UsersDBHelper1
import com.harshit.stockmanagementapp.model.OrderHistoryModel

class HistoryActivity : AppCompatActivity() {
    lateinit var usersDBHelper1: UsersDBHelper1
    lateinit var orderRecycler: RecyclerView
    lateinit var orderAdapter: OrderAdapter
    lateinit var layoutManager: LinearLayoutManager
    var listOfOrder = arrayListOf<OrderHistoryModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        title = "History"

        usersDBHelper1 = UsersDBHelper1(this)
        listOfOrder = usersDBHelper1.readAllUsers()
        orderRecycler = findViewById(R.id.recyclerOrderHistory)
        layoutManager = LinearLayoutManager(this)

        orderAdapter =
            OrderAdapter(this, listOfOrder)
        orderRecycler.layoutManager = layoutManager
        orderRecycler.itemAnimator = DefaultItemAnimator()
        orderRecycler.adapter = orderAdapter
        orderRecycler.setHasFixedSize(true)
    }
}