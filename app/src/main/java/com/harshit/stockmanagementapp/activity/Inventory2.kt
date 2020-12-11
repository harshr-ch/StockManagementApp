package com.harshit.stockmanagementapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harshit.stockmanagementapp.adapter.AllItemAdapter
import com.harshit.stockmanagementapp.R
import com.harshit.stockmanagementapp.database.UsersDBHelper

class Inventory2 : AppCompatActivity() {
    private lateinit var recyclerItem : RecyclerView
    private lateinit var allItemAdapter : AllItemAdapter
    lateinit var usersDBHelper: UsersDBHelper
    lateinit var rlEmpty : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory2)
        usersDBHelper =
            UsersDBHelper(this)
        val users = usersDBHelper.readAllUsers()
        val fab: View = findViewById(R.id.btnAdd)

        rlEmpty = findViewById(R.id.rlInventory)

        if(users.isEmpty())
            rlEmpty.visibility = View.VISIBLE
        else
            rlEmpty.visibility = View.GONE

        recyclerItem = findViewById(R.id.recyclerItem) as RecyclerView
        allItemAdapter =
            AllItemAdapter(users, this)
        val mLayoutManager = LinearLayoutManager(this)
        recyclerItem.layoutManager = mLayoutManager
        recyclerItem.itemAnimator = DefaultItemAnimator()
        recyclerItem.adapter = allItemAdapter
        recyclerItem.setHasFixedSize(true)

        title="Inventory"

        fab.setOnClickListener {
            intent = Intent(this@Inventory2, addItem::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@Inventory2,
            MainActivity::class.java))
    }
}