package com.harshit.stockmanagementapp.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.harshit.stockmanagementapp.adapter.ConfirmAdapter
import com.harshit.stockmanagementapp.R
import com.harshit.stockmanagementapp.database.UsersDBHelper
import com.harshit.stockmanagementapp.database.UsersDBHelper1
import com.harshit.stockmanagementapp.model.ArrayModel
import com.harshit.stockmanagementapp.model.OrderHistoryModel
import java.time.LocalDate

class Confirm : AppCompatActivity() {
    lateinit var usersDBHelper: UsersDBHelper
    lateinit var usersDBHelper1: UsersDBHelper1
    lateinit var btnConfirmOrder: Button
    private lateinit var recyclerItem: RecyclerView
    private lateinit var confirmAdapter: ConfirmAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        usersDBHelper =
            UsersDBHelper(this)
        usersDBHelper1 =
            UsersDBHelper1(this)
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder)
        var map: HashMap<String, Int> = HashMap()
        val users = ArrayList<ArrayModel>()

        title = "Confirm your order"

        if (intent != null) {
            map = intent.getSerializableExtra("map") as HashMap<String, Int>
        }
        var total = 0
        Log.e("map", "$map")
        for ((key, value) in map) {
            if (value > 0) {
                val user1 = usersDBHelper.readUser(key)
                total = total + (user1[0].price.toInt() * value.toInt())
                users.add(
                    ArrayModel(
                        user1[0].name,
                        value.toString(),
                        user1[0].price
                    )
                )
            }
        }

        recyclerItem = findViewById<RecyclerView>(R.id.confirmRecycler)
        confirmAdapter =
            ConfirmAdapter(users, this)
        val mLayoutManager = LinearLayoutManager(this)
        recyclerItem.layoutManager = mLayoutManager
        recyclerItem.itemAnimator = DefaultItemAnimator()
        recyclerItem.adapter = confirmAdapter
        recyclerItem.setHasFixedSize(true)
        btnConfirmOrder.text = "Confirm Order (Rs. " + total.toString() + ")"
        btnConfirmOrder.setBackgroundColor(resources.getColor(R.color.buttonColor))

        btnConfirmOrder.setOnClickListener {


            for ((key, value) in map) {
                usersDBHelper.update(key, value.toString())
            }
            val arrayofitem =  ArrayList<ArrayModel>()
            for ((key, value) in map) {
                if (value > 0) {
                    val user1 = usersDBHelper.readUser(key)
                    arrayofitem.add(
                        ArrayModel(
                            key,
                            value.toString(),
                            user1[0].price
                        )
                    )
                }
            }

            val current = LocalDate.now().toString()
            val arrayData: String = Gson().toJson(arrayofitem)
            val a = usersDBHelper1.insertUser(OrderHistoryModel(current,arrayData))

            Log.e("sahi","$a")

            startActivity(
                Intent(
                    this@Confirm,
                    FinalConfirm::class.java
                )
            )
        }
    }
}