package com.harshit.stockmanagementapp.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harshit.stockmanagementapp.adapter.BuyItemAdapter
import com.harshit.stockmanagementapp.R
import com.harshit.stockmanagementapp.model.UserModel
import com.harshit.stockmanagementapp.database.UsersDBHelper

class BuyActivity : AppCompatActivity() {
    private lateinit var recyclerItem: RecyclerView
    private lateinit var buyItemAdapter: BuyItemAdapter
    lateinit var usersDBHelper: UsersDBHelper
    lateinit var btnPlaceOrder: Button
    lateinit var rlInventory : RelativeLayout
    var max: Int = 0
    lateinit var users: ArrayList<UserModel>
    var map: HashMap<String, Int> = HashMap<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        usersDBHelper =
            UsersDBHelper(this)
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)
        rlInventory = findViewById(R.id.rlInventory)

        users = usersDBHelper.readAllUsers()

        users.forEach {
            map[it.name] = 0
        }

        if(users.isEmpty())
            rlInventory.visibility = View.VISIBLE
        else
            rlInventory.visibility = View.GONE

        title = "Add item to your cart"

        recyclerItem = findViewById<RecyclerView>(R.id.recyclerItem)
        buyItemAdapter =
            BuyItemAdapter(
                users,
                this,
                map
            )
        val mLayoutManager = LinearLayoutManager(this)
        recyclerItem.layoutManager = mLayoutManager
        recyclerItem.itemAnimator = DefaultItemAnimator()
        recyclerItem.adapter = buyItemAdapter
        recyclerItem.setHasFixedSize(true)

        btnPlaceOrder.setOnClickListener {
            max = 0
            users.forEach {
                if (max < map[it.name]!!.toInt())
                    max = map[it.name]!!.toInt()
            }
            if (max > 0) {
                val intent = Intent(this@BuyActivity, Confirm::class.java)
                intent.putExtra("map", map)
                startActivity(intent)
            } else
                Toast.makeText(this@BuyActivity, "Your cart is empty", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        max = 0
        users.forEach {
            if (max < map[it.name]!!.toInt())
                max = map[it.name]!!.toInt()
        }
        if (max > 0) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("CART IS NOT EMPTY")
            builder.setMessage("Going back will remove your item from cart!!!")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                startActivity(Intent(this@BuyActivity, MainActivity::class.java))
            }
            builder.setNegativeButton("No") { dialogInterface, which ->

            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        } else
            super.onBackPressed()
    }
}