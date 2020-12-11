package com.harshit.stockmanagementapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.harshit.stockmanagementapp.R

class MainActivity : AppCompatActivity() {
    lateinit var btnInventory: ImageView
    lateinit var btnBuy: ImageView
    lateinit var btnTransaction : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInventory = findViewById(R.id.btnInventory)
        btnBuy = findViewById(R.id.btnBuy)
        btnTransaction = findViewById(R.id.btnTransaction)

        title = "Dashboard"

        btnInventory.setOnClickListener {
            intent = Intent(
                this@MainActivity,
                Inventory2::class.java
            )
            startActivity(intent)
        }

        btnBuy.setOnClickListener {
            intent = Intent(
                this@MainActivity,
                BuyActivity::class.java
            )
            startActivity(intent)
        }
        btnTransaction.setOnClickListener {
            intent = Intent(
                this@MainActivity,
                HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}