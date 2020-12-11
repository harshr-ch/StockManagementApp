package com.harshit.stockmanagementapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.harshit.stockmanagementapp.R

class FinalConfirm : AppCompatActivity() {
    lateinit var btnOk : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_confirm)
        btnOk = findViewById(R.id.btnOk)
        supportActionBar!!.hide()
        btnOk.setOnClickListener {
            startActivity(Intent(this@FinalConfirm,
                MainActivity::class.java))
        }
    }
    override fun onBackPressed() {
    }

}